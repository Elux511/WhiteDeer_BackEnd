package com.WhiteDeer.service;

import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.Task;
import com.WhiteDeer.dao.TaskRepository;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.TaskDTO;
import com.WhiteDeer.util.GeoDistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void createTask(TaskDTO taskDTO) {
        taskRepository.save(TaskConverter.convertTask(taskDTO));
    }

    @Override
    public TaskDTO getTaskById(Long id) {
       Task task = taskRepository.getById(id);
       return TaskConverter.convertTask(task);
    }

    @Override
    public void deleteUserById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("打卡任务ID不存在: " + id));
        taskRepository.delete(task); //删除所查询到的实体
    }

    @Override
    public int checkinTask(TaskDTO taskDTO) {
        Task task = taskRepository.getById(taskDTO.getId());
        if(taskDTO.getType() == "人脸打卡"){

        }else if(taskDTO.getType() == "定位打卡"){
            double distance = GeoDistanceCalculator.haversine(task.getLatitude(),task.getLongitude(),taskDTO.getLatitude(),taskDTO.getLongitude());
            if(distance < task.getAccuracy()){//打卡位置在允许范围内
                return 1;
            }else{//打卡位置不在允许范围内
                return 3;
            }
        }else{
            double distance = GeoDistanceCalculator.haversine(task.getLatitude(),task.getLongitude(),taskDTO.getLatitude(),taskDTO.getLongitude());
            if(distance < task.getAccuracy()){//打卡位置在允许范围内

            }else{//打卡位置不在允许范围内
                return 3;
            }
        }
    }

}
