package com.WhiteDeer.Controller;

import com.WhiteDeer.Response;
import com.WhiteDeer.dto.GroupDetailDTO;
import com.WhiteDeer.dto.GroupInfoDTO;
import com.WhiteDeer.service.GroupInfoService;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GroupController {

    @Autowired
    private GroupInfoService groupInfoService;

    //获取用户加入的团队列表
    //(GET) http://localhost:8080/api/getjoinedgroups?id=xxxxxxxx
    //	请求体：无
    //返回:{
    //“state”（1（成功）；2（失败）），
    //“data”:{
    //		“joinedGroups”:[{},{}]
    //（每个group对象格式：
    //“groupId”:1,
    //“groupName”:'茧之泪殇',
    //“isFull”:"否",
    //“memberCount”:'123'
    //“createTime”:'2024-10-01 09:15:00'）
    //}
    //}
    @GetMapping("/api/getjoinedgroups")
    public Response<Map<String, Object>> getJoinedGroups(@RequestParam("id") Long userId) {

        try {
            Map<String, Object> data = new HashMap<>();
            List<GroupInfoDTO> joinedGroups = groupInfoService.getJoinedGroups(userId);
            data.put("joinedGroups", joinedGroups);
            data.put("id", userId);
            return Response.newSuccess(1,data);
        } catch (IllegalIdentifierException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", userId);
            data.put("message", e.getMessage());
            return Response.newFailed(2,data);
        }
    }

    // 获取用户管理的团队列表
    //(GET) http://localhost:8080/api/getmanagedgroups?id=xxxxxxxx
    //请求体：无
    //返回{
    //“state”（1（成功）；2（失败）），
    //“data”:{
    //		“managedGroups”:[{},{}]
    //（每个group对象格式：
    //“groupId”:1,
    //“groupName”:'茧之泪殇',
    //“isFull“:"否",
    //“memberCount“:'123'
    //“createTime“:'2024-10-01 09:15:00'）
    //}
    //}
    @GetMapping("/api/getmanagedgroups")
    public Response<Map<String, Object>> getManagedGroups(@RequestParam("id") Long userId) {
        try {
            Map<String, Object> data = new HashMap<>();
            List<GroupInfoDTO> managedGroups = groupInfoService.getManagedGroups(userId);
            data.put("manegedGroups", managedGroups);
            data.put("id", userId);
            return Response.newSuccess(1,data);
        } catch (IllegalIdentifierException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id",userId);
            data.put("message", e.getMessage());
            return Response.newFailed(2,data);
        }

    }

    // .搜索团队
    //(GET) http://localhost:8080/api/searchgroup?id=xxxxxx&name=xxxxxx&begin=xxxxxxxx&end=xxxxxxxx
    //请求体：无
    //返回:{
    //“state”（1（返回成功）；2（错误）），
    //“data”:{
    //“groups”:[{}，{}]
    //(所有满足信息的团队列表，每个团队对象格式：
    //“groupId“:"139834",
    //“groupName“:'测试数据1',
    //“isFull“:"否",
    //“memberCount“:'123',
    //“createTime“:'2025-5-11 09:15:00')
    //}
    //（注：如果id不为空则只有一个团队，如果id是空串可能有多个团队符合情况，begin和end是团队创建时间区间格式为ISO 8601 标准时间字符串，如：2025-05-06T16:00:00.000Z)
    //}
    @GetMapping("/api/searchgroup")
    public Response<Map<String, Object>> searchGroup(@RequestParam(required = false) String id,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String begin,
                                          @RequestParam(required = false) String end) {
        try {
            Map<String, Object> data = new HashMap<>();
            List<GroupInfoDTO> groups = groupInfoService.searchGroups(id, name, begin, end);
            data.put("groups", groups);
            return Response.newSuccess(1,data);
        } catch (IllegalIdentifierException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("message", e.getMessage());
            return Response.newFailed(2,data);
        }
    }

    // 加入团队
    //（POST）http://localhost:8080/api/joingroup
    //请求体：{
    //“id”:12345678，
    //“groupId“:123456
    //}
    //返回:{“state”（1（加入成功）；2（加入失败）；3（已加入该团队）}
    //如果已加入返回state3
    //
     //
     //
     //
     //
    @PostMapping("/api/joingroup")
    public Response<Map<String, Object>> joinGroup(@RequestBody Map<String, Object> payload) {
        try {
            Map<String, Object> data = new HashMap<>();
            Long userId = Long.valueOf(payload.get("id").toString());
            Long groupId = Long.valueOf(payload.get("groupId").toString());
            int success = groupInfoService.joinGroup(userId, groupId);
            if (success  == 1) {
                data.put("id", userId);
                data.put("groupId", groupId);
                return Response.newSuccess(1,data);
            }else if(success == 2){
                return Response.newFailed(3,data);
            }else {
                return Response.newFailed(2,data);
            }
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", payload.get("id"));
            data.put("message", e.getMessage());
            return Response.newFailed(2,data);
        }

    }

    //创建团队
    //（POST）http://localhost:8080/api/creategroup
    //请求体：{
    //“id”:123456 //创建者的id
    //“groupName”:“团队1“，
    //“maxMember“:123，//(最大人数)
    //“introduction”:“团队介绍”
    //}
    //返回:{“state”（1（创建成功）；2（失败） }
    @PostMapping("/api/creategroup")
    public Response<Map<String, Object>> createGroup(@RequestBody Map<String, Object> payload) {

        try {
            Map<String, Object> data = new HashMap<>();
            String groupName = (String) payload.get("groupName");
            Long maxMember = Long.parseLong(payload.get("maxMember").toString());
            String introduction = (String) payload.get("introduction");
            Long creatorId = Long.valueOf(payload.get("id").toString());
            boolean success = groupInfoService.createGroup(groupName, maxMember, introduction, creatorId);
            return Response.newSuccess(1,data);
        } catch (IllegalArgumentException e) {
            Map<String,Object> data = new HashMap<>();
            data.put("id", payload.get("id"));
            data.put("message", e.getMessage());

           return Response.newFailed(2,data);
        }
    }

    // 解散我创建的团队
    //（DELETE）http://localhost:8080/api/deletegroup? userId=123456&groupId=123456
    //请求体:无
    //返回：{state:(1(成功) 2（失败）)}
    @DeleteMapping("/api/deletegroup")
    public Response<Map<String, Object>> deleteGroup(@RequestParam("userId") Long userId,@RequestParam("groupId") Long groupId) {
        try{
            Map<String, Object> data = new HashMap<>();
            boolean success = groupInfoService.deleteGroup(userId, groupId);
            return Response.newSuccess(1,data);
        }catch(IllegalArgumentException e){
            Map<String,Object> data = new HashMap<>();
            data.put("userId", userId);
            data.put("groupId", groupId);
            data.put("message", e.getMessage());
            return Response.newFailed(2,data);
        }
    }

    //.查看创建的某个团队的信息
    //（GET）http://localhost:8080/api/group?groupId=xxxxxx
    //请求体：无
    //返回:{
    //“state”（1（成功）2（失败））；
    //“data”:{
    //	“memberlist":[{},{}],(每个member对象格式实例：
    //"id":"1",
    //"name":"茧之泪殇",
    //"phoneNumber":13013013130）
    //	“tasklist”:[{},{}],（每个task对象格式实例：
    //"id": 2,
    //          "name": "测试数据2",
    //          "endTime": "2024-10-01T09:30:00.000Z",
    //          "shouldCount": 10,
    //          "actualCount": 9)
    //}
    //}
    @GetMapping("/api/group")
    public Response<Map<String, Object>> getGroup(@RequestParam("groupId") Long groupId) {
        try {
            GroupDetailDTO groupDetail = groupInfoService.getGroupDetails(groupId);
            Map<String, Object> data = new HashMap<>();
            data.put("memberlist", groupDetail.getMemberlist());
            data.put("tasklist", groupDetail.getTasklist());
            return Response.newSuccess(1,data);
        } catch (IllegalArgumentException e) {
            Map<String,Object> data = new HashMap<>();
            data.put("id", groupId);
            data.put("message",  e.getMessage());
            return Response.newFailed(2,data);
        }catch(Exception e){
            Map<String,Object> data = new HashMap<>();
            data.put("id", groupId);
            data.put("message", e.getMessage());
            return Response.newFailed(2,data);
        }
    }





    //退出团队
    //(POST)http://localhost:8080/api/quitgroup
    //	请求体：{
    //“id”：12345678，
    //“groupId”:123456
    //}
    //返回:{“state”（1（退出成功）；2(失败)}
    @PostMapping("/api/quitgroup")
    public Response<Map<String, Object>> quitGroup(@RequestBody Map<String, Object> payload) {

        try {
            Map<String, Object> data = new HashMap<>();
            Long userId = Long.valueOf(payload.get("id").toString());
            Long groupId = Long.valueOf(payload.get("groupId").toString());
            boolean success = groupInfoService.quitGroup(userId, groupId);
            return Response.newSuccess(1,data);
        } catch (IllegalArgumentException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", payload.get("id"));
           return Response.newFailed(2,data);
        }catch(Exception e){
            Map<String,Object> data = new HashMap<>();
            data.put("id", payload.get("id"));
            data.put("message", e.getMessage());
            return Response.newFailed(2,data);
        }
    }


}
