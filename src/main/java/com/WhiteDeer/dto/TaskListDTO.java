package com.WhiteDeer.dto;

import java.util.Vector;

public class TaskListDTO {
    private Vector<TaskDTO> checkinList;

    public TaskListDTO(Vector<TaskDTO> taskList) {
        this.checkinList = taskList;
    }

    public Vector<TaskDTO> getTasks() {
        return checkinList;
    }
    public void setTasks(Vector<TaskDTO> tasks) {
        this.checkinList = tasks;
    }

}
