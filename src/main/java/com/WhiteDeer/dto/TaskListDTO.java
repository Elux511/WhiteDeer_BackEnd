package com.WhiteDeer.dto;

import java.util.Vector;

public class TaskListDTO {
    private Vector<TaskDTO> checkinList;

    public TaskListDTO(Vector<TaskDTO> taskList) {
        this.checkinList = taskList;
    }

    public Vector<TaskDTO> getCheckinList() {
        return checkinList;
    }
    public void setCheckinList(Vector<TaskDTO> checkinList) {
        this.checkinList = checkinList;
    }

}



