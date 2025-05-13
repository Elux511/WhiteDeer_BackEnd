package com.WhiteDeer.dto;

import java.util.Vector;

public class TaskListDTO {
    private Vector<TaskDTO> tasks;

    public TaskListDTO(Vector<TaskDTO> taskList) {
        this.tasks = taskList;
    }

    public Vector<TaskDTO> getTasks() {
        return tasks;
    }
    public void setTasks(Vector<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
