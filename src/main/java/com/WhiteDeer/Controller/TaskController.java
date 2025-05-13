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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    //发布新的打卡任务
    @PostMapping("/api/createtask")
    public Response<Void> createTask(@RequestBody TaskDTO taskDTO) throws IllegalAccessException {
        taskService.createTask(taskDTO);
        return Response.newState(1);
    }

    //初始化打卡列表
    @GetMapping("/api/mycheckin")
    public Response<List<Pair<TaskDTO,String>>> getTask(@RequestParam long id) {
        Optional<UserDTO> userOPT = userService.getUserById(id);
        List<Pair<TaskDTO,String>> taskList = new ArrayList<>();

        if(userOPT.isEmpty()) {
            return Response.newFailed(2,taskList);
        }
        UserDTO userDTO = userOPT.get();
        for(Long taskId : userDTO.getYesTaskSet())
        {
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            Pair<TaskDTO,String> pair = Pair.of(taskDTO,"completed");
            taskList.add(pair);
        }
        for(Long taskId : userDTO.getNoTaskSet())
        {
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            Pair<TaskDTO,String> pair = Pair.of(taskDTO,"uncompleted");
            taskList.add(pair);
        }
        return Response.newSuccess(1,taskList);
    }

    //删除打卡任务
    @DeleteMapping("/api/deletetask")
    public Response<Void> deleteTask(@RequestParam long id) {
        try{
            taskService.deleteTaskById(id);
            return Response.newState(1);
        }catch (IllegalArgumentException e){
            return Response.newState(2);
        }
    }

    //打卡
    @PostMapping("/api/checkin")
    public Response<Void> checkinTask(@RequestParam long id, @RequestBody TaskDTO taskDTO) throws IllegalAccessException, IOException {
        Optional<UserDTO> userOPT = userService.getUserById(id);
        if(userOPT.isEmpty()) {
            return Response.newState(4);
        }
        UserDTO userDTO = userOPT.get();
        int result = taskService.checkinTask(taskDTO,userDTO.getId());
        if(result == 1) {
            userService.finishTaskById(userDTO.getId(),taskDTO.getId());
            taskService.finishTaskById(userDTO.getId(),taskDTO.getId());
        }
        return Response.newState(result);
    }
}
