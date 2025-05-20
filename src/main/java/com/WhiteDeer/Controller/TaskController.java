package com.WhiteDeer.Controller;

import com.WhiteDeer.Response;
import com.WhiteDeer.converter.TaskConverter;
import com.WhiteDeer.dao.GroupInfo;
import com.WhiteDeer.dao.Task;
import com.WhiteDeer.dao.User;
import com.WhiteDeer.dto.*;
import com.WhiteDeer.service.GroupInfoService;
import com.WhiteDeer.service.TaskService;
import com.WhiteDeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired(required=true)
    private UserService userService;

    @Autowired
    private GroupInfoService groupInfoService;

    //发布新的打卡任务
    @PostMapping("/api/createtask")
    public Response<Void> createTask(@RequestBody TaskDTO taskDTO) throws IllegalAccessException {
        Task task = taskService.createTask(taskDTO);

        //为团队发布任务
        GroupInfo groupInfo = groupInfoService.getGroupInfo(taskDTO.getGroupId());
        if(groupInfo == null){
            return Response.newState(2);
        }
        if(groupInfo.getNoTaskSet()==null){
            groupInfo.setNoTaskSet(new Vector<>());
        }
        groupInfo.getNoTaskSet().add(task.getId());
        

        GroupDetailDTO groupDetailDTO = groupInfoService.getGroupDetails(taskDTO.getGroupId());
        if(groupDetailDTO == null){return Response.newState(2);}//检测是否存在团队
        if(groupDetailDTO.getMemberlist() == null){return Response.newState(1);}//检测团队列表是否为空

        Vector<Long> incomplete = new Vector<>();
        //为所有团队成员发布打卡任务
        for(MemberDTO member : groupDetailDTO.getMemberlist()){
            long userId = member.getId();
            userService.acceptTaskById(userId,task.getId());
            incomplete.add(userId);
        }

        //将所有成员加入到task的未完成列表
        task.setCompletedUserList(new Vector<>());
        task.setIncompleteUserList(incomplete);
        //设置应到实到
        task.setShouldCount(groupDetailDTO.getMemberlist().size());
        task.setActualCount(0);
        taskService.updateTask(task);

        return Response.newState(1);
    }

    //初始化打卡列表
    @GetMapping("/api/mycheckin")
    public Response<TaskListDTO> getTask(@RequestParam long id) {
        Optional<UserDTO> userOPT = userService.getUserById(id);
        Vector<TaskDTO> taskList = new Vector<>();
        TaskListDTO taskListDTO = new TaskListDTO(taskList);

        if(userOPT.isEmpty()) {
            return Response.newFailed(2,taskListDTO);
        }
        UserDTO userDTO = userOPT.get();
        if(userDTO.getYesTaskSet() == null && userDTO.getNoTaskSet() == null) {return Response.newSuccess(1,taskListDTO);}
        if(userDTO.getYesTaskSet() == null){userDTO.setYesTaskSet(new Vector<>());}
        if(userDTO.getNoTaskSet() == null){userDTO.setNoTaskSet(new Vector<>());}
        for(Long taskId : userDTO.getYesTaskSet())
        {
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            if(taskDTO == null){
                return Response.newFailed(2,taskListDTO);
            }
            GroupInfo groupInfo = groupInfoService.getGroupInfo(taskDTO.getGroupId());
            taskDTO.setGroupName(groupInfo.getGroupName());
            taskDTO.setStatus("completed");
            taskList.add(taskDTO);
        }
        for(Long taskId : userDTO.getNoTaskSet())
        {
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            if(taskDTO == null){
                return Response.newFailed(2,taskListDTO);
            }
            GroupInfo groupInfo = groupInfoService.getGroupInfo(taskDTO.getGroupId());
            taskDTO.setGroupName(groupInfo.getGroupName());
            taskDTO.setStatus("incomplete");
            taskList.add(taskDTO);
        }
        return Response.newSuccess(1,taskListDTO);
    }

    //删除打卡任务
    @DeleteMapping("/api/deletetask")
    public Response<String> deleteTask(@RequestParam long id) {
        try{
            TaskDTO taskDTO = taskService.getTaskById(id);
            taskService.deleteTaskById(id);
            //团队删除打卡任务
            String msg1 = groupInfoService.deleteTaskById(taskDTO.getGroupId(),id);
            if(!msg1.equals("团队删除成功")){return Response.newFailed(2,msg1);}

            //成员删除打卡任务
            String msg2 = "";
            GroupDetailDTO groupDetailDTO = groupInfoService.getGroupDetails(taskDTO.getGroupId());
            if (groupDetailDTO == null){return Response.newFailed(2,"未找到团队");}
            if(groupDetailDTO.getMemberlist() == null){return Response.newFailed(2,"未找到团队成员");}
            for(MemberDTO member : groupDetailDTO.getMemberlist()){
                long userId = member.getId();
                userService.deleteTaskById(userId,id);
            }
            return Response.newSuccess(1,"用户删除打卡任务完成");
        }catch (IllegalArgumentException e){
            return Response.newFailed(2,"未找到任务");
        }
    }

    //打卡
    @PostMapping(value="/api/checkin",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Void> checkinTask(@RequestParam("id") long userId,@RequestParam("taskId") long taskId, @RequestParam("type") String type,
                                      @RequestPart(value = "face",required = false) MultipartFile face,
                                      @RequestParam(value = "latitude") String latitudeStr,
                                      @RequestParam(value = "longitude") String longitudeStr) throws IllegalAccessException, IOException {
        //通过id获取taskDTO，后面用
        TaskDTO taskDTO = taskService.getTaskById(taskId);

        //检验是否超时
        if (LocalDateTime.now().isBefore(taskDTO.getBeginTime()) || LocalDateTime.now().isAfter(taskDTO.getEndTime())) {return Response.newState(5);}

        //将获取到的face传给taskDTO
        if (face!=null) {
            try {
                Blob blob = new SerialBlob(face.getBytes());
                taskDTO.setFace(blob);
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
        //将获取到的经纬度传给taskDTO
        if(type.equals("定位打卡")||type.equals("都")){
            //对经纬度进行处理
            double latitude = 0.0;
            if (latitudeStr != null && !latitudeStr.isEmpty()) {latitude = Double.parseDouble(latitudeStr);}
            double longitude = 0.0;
            if (longitudeStr != null && !longitudeStr.isEmpty()) {longitude = Double.parseDouble(longitudeStr);}

            taskDTO.setLatitude(latitude);
            taskDTO.setLongitude(longitude);
        }

        Optional<UserDTO> userOPT = userService.getUserById(userId);
        if(userOPT.isEmpty()) {
            return Response.newState(4);
        }
        UserDTO userDTO = userOPT.get();
        //打卡过程
        int result = taskService.checkinTask(taskDTO,userDTO.getId());
        //打卡成功后
        if(result == 1) {
            userService.finishTaskById(userId,taskId);
            taskService.finishTaskById(userId,taskId);
        }

        //如果所有人都打卡成功，就在团队里进行更新
        TaskDTO taskTemp = taskService.getTaskById(taskId);
        if(taskTemp.getIncompleteUserList().isEmpty()) {
            groupInfoService.finishTaskById(taskTemp.getGroupId(),taskId);
        }
        return Response.newState(result);
    }

    //查询task详细信息
    @GetMapping("/api/task")
    public Response<TaskDTO> getTasks(@RequestParam long id) throws IllegalAccessException {
        TaskDTO taskDTO = taskService.getTaskById(id);
        if(taskDTO == null) {return Response.newFailed(2,taskDTO);}
        Vector<String> completed = new Vector<>();
        Vector<String> incomplete = new Vector<>();
        if(taskDTO.getCompletedUserList() != null) {//判断是否非空
            for(Long userId : taskDTO.getCompletedUserList())//添加到已完成用户名单
            {
                Optional<UserDTO> userOPT = userService.getUserById(userId);
                if(userOPT.isEmpty()) {
                    return  Response.newFailed(2,taskDTO);
                }
                completed.add(userOPT.get().getName());
            }
        }
        if(taskDTO.getIncompleteUserList() != null) {//判断是否非空
            for(Long userId : taskDTO.getIncompleteUserList())//添加到未完成用户名单
            {
                Optional<UserDTO> userOPT = userService.getUserById(userId);
                if(userOPT.isEmpty()) {
                    return  Response.newFailed(2,taskDTO);
                }
                incomplete.add(userOPT.get().getName());
            }
        }


        taskDTO.setCompletedNameList(completed);
        taskDTO.setIncompleteNameList(incomplete);
        return Response.newSuccess(1,taskDTO);
    }
}
