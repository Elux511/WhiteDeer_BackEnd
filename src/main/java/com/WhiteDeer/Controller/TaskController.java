package com.WhiteDeer.Controller;

import com.WhiteDeer.Response;
import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.TaskDTO;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.service.TaskService;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public Response<List<Pair<TaskDTO,String>>> getTask(@RequestParam long id) {
        System.out.println(1);
        UserDTO userDTO = userService.getUserById(id);
        System.out.println(2);
        List<Pair<TaskDTO,String>> taskList = new ArrayList<>();
        for(Long taskId : userDTO.getYesTaskSet())
        {
            System.out.println(3);
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            System.out.println(6);
            Pair<TaskDTO,String> pair = Pair.of(taskDTO,"completed");
            taskList.add(pair);
        }
        for(Long taskId : userDTO.getNoTaskSet())
        {
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            Pair<TaskDTO,String> pair = Pair.of(taskDTO,"uncompleted");
            taskList.add(pair);
        }
        return Response.newSuccess(taskList);
    }
}
