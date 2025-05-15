package com.WhiteDeer.Controller;

import com.WhiteDeer.Response;
import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.GroupDetailDTO;
import com.WhiteDeer.dto.MemberDTO;
import com.WhiteDeer.dto.TaskDTO;
import com.WhiteDeer.dto.UserDTO;
import com.WhiteDeer.service.GroupInfoService;
import com.WhiteDeer.service.TaskService;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupInfoService groupInfoService;

    //发布新的打卡任务
    @PostMapping("/api/createtask")
    public Response<Void> createTask(@RequestBody TaskDTO taskDTO) throws IllegalAccessException {
        GroupDetailDTO groupDetailDTO = groupInfoService.getGroupDetails(taskDTO.getGroupId());
        if(groupDetailDTO.getMemberlist().isEmpty()){
            return Response.newState(2);
        }

        Vector<Long> incomplete = new Vector<>();
        //为所有团队成员发布打卡任务
        for(MemberDTO member : groupDetailDTO.getMemberlist()){
            long userId = member.getId();
            userService.acceptTaskById(userId,taskDTO.getId());
            incomplete.add(taskDTO.getId());
        }

        //将所有成员加入到task的未完成列表
        taskDTO.setIncompleteUserList(incomplete);
        //设置应到实到
        taskDTO.setShouldCount(groupDetailDTO.getMemberlist().size());
        taskDTO.setActualCount(0);
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
        if(userDTO.getYesTaskSet() == null || userDTO.getNoTaskSet() == null){
            return Response.newSuccess(1,taskList);
        }
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

    //查询task详细信息
    @GetMapping("/api/task")
    public Response<TaskDTO> getTasks(@RequestParam long id) throws IllegalAccessException {
        TaskDTO taskDTO = taskService.getTaskById(id);
        Vector<String> completed = new Vector<>();
        Vector<String> incomplete = new Vector<>();
        if(taskDTO.getCompletedUserList() != null) {//判断是否非空
            for(Long userId : taskDTO.getCompletedUserList())//添加到已完成用户名单
            {
                Optional<UserDTO> userOPT = userService.getUserById(userId);
                userOPT.ifPresentOrElse(userDTO -> {
                            completed.add(userDTO.getName());},
                        () -> {
                            throw new IllegalArgumentException("用户ID不存在: " + userId);
                        });
            }
        }
        if(taskDTO.getIncompleteUserList() != null) {//判断是否非空
            for(Long userId : taskDTO.getIncompleteUserList())//添加到未完成用户名单
            {
                Optional<UserDTO> userOPT = userService.getUserById(userId);
                userOPT.ifPresentOrElse(userDTO -> {
                            incomplete.add(userDTO.getName());},
                        () -> {
                            throw new IllegalArgumentException("用户ID不存在: " + userId);
                        });
            }
        }


        taskDTO.setCompletedNameList(completed);
        taskDTO.setIncompleteNameList(incomplete);
        return Response.newSuccess(1,taskDTO);
    }
}
