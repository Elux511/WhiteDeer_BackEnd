package com.WhiteDeer.service;

import com.WhiteDeer.converter.BlobConverter;
import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.Task;
import com.WhiteDeer.dao.TaskRepository;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.TaskDTO;
import com.WhiteDeer.util.GeoDistanceCalculator;
import com.WhiteDeer.util.PyAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Vector;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    //创建task
    @Override
    public void createTask(TaskDTO taskDTO) {
        taskDTO.setCompletedUserList(new Vector<>());
        taskDTO.setIncompleteUserList(new Vector<>());
        taskRepository.save(TaskConverter.convertTask(taskDTO));
    }

    //通过id获取task
    @Override
    public TaskDTO getTaskById(Long id) throws NoSuchElementException {
       Optional<Task> task = taskRepository.findById(id);
       return TaskConverter.convertTask(task.orElseThrow());
    }

    //通过id删除task
    @Override
    public void deleteTaskById(long id) {
        taskRepository.findById(id).ifPresentOrElse(
                task -> taskRepository.delete(task),
                () -> { throw new IllegalArgumentException("打卡任务ID不存在: " + id); }
        );
    }

    //用户打卡过程
    @Override
    public int checkinTask(TaskDTO taskDTO, long userId) {
        Task task = taskRepository.getById(taskDTO.getId());
        if(taskDTO.getType().equals("人脸识别")){
            String img = null;
            //尝试将Blob转为base64
            try {img = BlobConverter.blobToBase64(taskDTO.getFace());} catch (IOException e) {return 2;}
            if(PyAPI.faceRecognition(String.valueOf(userId),img)){
                //人脸识别成功
                return 1;
            }else {
                //人脸识别失败
                return 2;
            }
        }else if(taskDTO.getType().equals("定位打卡")){
            double distance = GeoDistanceCalculator.haversine(task.getLatitude(),task.getLongitude(),taskDTO.getLatitude(),taskDTO.getLongitude());
            if(distance <= task.getAccuracy()){
                //打卡位置在允许范围内
                return 1;
            }else{
                //打卡位置不在允许范围内
                return 3;
            }
        }else{
            String img = null;
            try {img = BlobConverter.blobToBase64(taskDTO.getFace());} catch (IOException e) {return 2;}//尝试将Blob转为base64
            double distance = GeoDistanceCalculator.haversine(task.getLatitude(),task.getLongitude(),taskDTO.getLatitude(),taskDTO.getLongitude());
            if(distance <= task.getAccuracy()){
                //打卡位置在允许范围内
                if(PyAPI.faceRecognition(String.valueOf(userId),img)){
                    //人脸识别成功
                    return 1;
                }else {
                    //人脸识别失败
                    return 2;
                }
            }else{
                //打卡位置不在允许范围内
                return 3;
            }
        }
    }

    //用户打卡完成后更新
    @Override
    public void finishTaskById(long userId, long taskId) {
        //System.out.println(4); 测试代码记得删
        Task task = taskRepository.getById(taskId);
        task.addCompletedUser(userId);
        task.deleteIncompleteUser(userId);
        taskRepository.save(task);
    }

}
