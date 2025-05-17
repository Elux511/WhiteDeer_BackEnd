package com.WhiteDeer.converter;


import com.WhiteDeer.dao.Task;
import com.WhiteDeer.dto.TaskDTO;

public class TaskConverter {
    public static TaskDTO convertTask(Task task){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setGroupId(task.getGroupId());
        taskDTO.setGroupName(task.getGroupName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setBeginTime(task.getBeginTime());
        taskDTO.setEndTime(task.getEndTime());
        taskDTO.setType(task.getType());
        taskDTO.setShouldCount(task.getShouldCount());
        taskDTO.setActualCount(task.getActualCount());
        taskDTO.setCompletedUserList(task.getCompletedUserList());
        taskDTO.setIncompleteUserList(task.getIncompleteUserList());
        taskDTO.setLatitude(task.getLatitude());
        taskDTO.setLongitude(task.getLongitude());
        taskDTO.setAccuracy(task.getAccuracy());
        return taskDTO;
    }

    public static Task convertTask(TaskDTO taskDTO){
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setGroupId(taskDTO.getGroupId());
        task.setBeginTime(taskDTO.getBeginTime());
        task.setEndTime(taskDTO.getEndTime());
        task.setType(taskDTO.getType());
        task.setDescription(taskDTO.getDescription());
        task.setQRcode(taskDTO.isQRcode());
        task.setCompletedUserList(taskDTO.getCompletedUserList());
        task.setIncompleteUserList(taskDTO.getIncompleteUserList());
        if(taskDTO.getType().equals("定位打卡") || taskDTO.getType().equals("都"))
        {
            task.setLatitude(taskDTO.getLatitude());
            task.setLongitude(taskDTO.getLongitude());
            task.setAccuracy(taskDTO.getAccuracy());
        }
        task.setShouldCount(taskDTO.getShouldCount());
        task.setActualCount(taskDTO.getActualCount());
        return task;
    }
}
