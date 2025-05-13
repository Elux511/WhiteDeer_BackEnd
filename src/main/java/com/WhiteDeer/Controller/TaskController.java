package com.WhiteDeer.Controller;

import com.WhiteDeer.Response;
import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.TaskDTO;
import com.WhiteDeer.dto.TaskListDTO;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.service.TaskService;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;


    @PostMapping("/api/createtask")
    public Response<Void> createTask(@RequestBody TaskDTO taskDTO) throws IllegalAccessException {
        taskService.createTask(taskDTO);
        return Response.newstate(1);
    }

    @GetMapping("/api/mycheckin")
    public Response<TaskListDTO> getTask(@RequestParam long id) {
        System.out.println(1);
        UserDTO userDTO = userService.getUserById(id);
        System.out.println(2);
        Vector<TaskDTO> taskList = new Vector<>();
        TaskListDTO taskListDTO = new TaskListDTO(taskList);
        for(Long taskId : userDTO.getYesTaskSet())
        {
            System.out.println(3);
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            System.out.println(6);
            taskDTO.setStatus("completed");
            taskList.add(taskDTO);
        }
        for(Long taskId : userDTO.getNoTaskSet())
        {
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            taskDTO.setStatus("incomplete");
            taskList.add(taskDTO);
        }
        return Response.newSuccess(taskListDTO);
    }
}
