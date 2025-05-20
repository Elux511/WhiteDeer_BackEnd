package com.WhiteDeer.service;

import com.WhiteDeer.cache.TaskCache;
import com.WhiteDeer.converter.BlobConverter;
import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.Task;
import com.WhiteDeer.dao.TaskRepository;
import com.WhiteDeer.dto.TaskDTO;
import com.WhiteDeer.util.GeoDistanceCalculator;
import com.WhiteDeer.util.PyAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Vector;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCache taskCache;


    //创建task
    @Override
    @Transactional
    public Task createTask(TaskDTO taskDTO) {
        Task task = taskRepository.save(TaskConverter.convertTask(taskDTO));
        taskCache.saveTask(task); // 更新缓存
        return task;
    }

    @Override
    @Transactional
    public void updateTask(Task task){
        taskRepository.save(task);
        taskCache.saveTask(task);
    }

    //通过id获取task
    @Override
    @Transactional
    public TaskDTO getTaskById(Long id) throws NoSuchElementException {
        // 先查缓存
        Optional<Task> cachedTask = taskCache.getTaskById(id);
        if (cachedTask.isPresent()) {
            return TaskConverter.convertTask(cachedTask.get());
        }

        // 缓存未命中，查数据库
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            taskCache.saveTask(taskOpt.get()); // 写入缓存
            return TaskConverter.convertTask(taskOpt.get());
        }
        return null;
    }

    //通过id删除task
    @Override
    @Transactional
    public void deleteTaskById(long id) {
        taskRepository.findById(id).ifPresentOrElse(
                task -> {
                    taskRepository.delete(task);
                    taskCache.deleteTask(id); // 删除缓存
                },
                () -> { throw new IllegalArgumentException("打卡任务ID不存在: " + id); }
        );
    }

    //用户打卡过程
    @Override
    public int checkinTask(TaskDTO taskDTO, long userId) {
        //这个task里存储的是任务的各项要求，taskDTO里则是存储用户的各种条件
        Task task = taskRepository.getById(taskDTO.getId());
        if(taskDTO.getType().equals("人脸识别")){
            String img = null;
            try {img = BlobConverter.blobToBase64(taskDTO.getFace());} catch (IOException e) {return 2;}//尝试将Blob转为base64
            if(PyAPI.faceRecognition(String.valueOf(userId),img)){//人脸识别成功
                return 1;
            }else {//人脸识别失败
                return 2;
            }
        }else if(taskDTO.getType().equals("定位打卡")){
            double distance = GeoDistanceCalculator.haversine(task.getLatitude(),task.getLongitude(),taskDTO.getLatitude(),taskDTO.getLongitude());
            if(distance <= task.getAccuracy()){//打卡位置在允许范围内
                return 1;
            }else{//打卡位置不在允许范围内
                return 3;
            }
        }else{
            String img = null;
            try {img = BlobConverter.blobToBase64(taskDTO.getFace());} catch (IOException e) {return 2;}//尝试将Blob转为base64
            double distance = GeoDistanceCalculator.haversine(task.getLatitude(),task.getLongitude(),taskDTO.getLatitude(),taskDTO.getLongitude());
            if(distance <= task.getAccuracy()){//打卡位置在允许范围内
                if(PyAPI.faceRecognition(String.valueOf(userId),img)){//人脸识别成功
                    return 1;
                }else {//人脸识别失败
                    return 2;
                }
            }else{//打卡位置不在允许范围内
                return 3;
            }
        }
    }

    //用户打卡完成后
    @Override
    @Transactional
    public void finishTaskById(long userId, long taskId) {
        Task task = taskRepository.getById(taskId);
        task.addCompletedUser(userId);
        task.deleteIncompleteUser(userId);
        task.setActualCount(task.getActualCount()+1);
        updateTask(task);
    }

}
