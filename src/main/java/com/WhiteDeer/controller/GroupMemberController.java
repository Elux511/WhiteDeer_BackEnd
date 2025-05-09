import com.WhiteDeer.entity.GroupMember;
import com.WhiteDeer.mapper.dto.GroupMemberDto;
import com.WhiteDeer.mapper.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.WhiteDeer.service.GroupMemberService;


@RestController//返回对象，直接转化为json文本
@RequestMapping("/GroupMember")
public class GroupMemberController {
    @Autowired
    GroupMemberService groupmemberService;


    //增加打卡任务
    @PostMapping
    public String add(@Validated @RequestBody GroupMemberDto task) {
        GroupMember groupmemberNew=groupmemberService.add(task);
        return ResponseMessage.success(groupmemberNew);
    }
    //查询打卡任务
    @GetMapping("/{groupmemberId}")
    public ResponseMessage get(@PathVariable Integer groupmemberId){
        GroupMember groupmemberNew=GroupMember.getGroupMember(groupmemberId);
        return ResponseMessage.success(groupmemberNew);
    }
    //修改
    @PutMapping
    public ResponseMessage edit(@PathVariable Integer taskId){
        GroupMember GroupMemberNew1=groupmemberService.edit(taskId);
        return ResponseMessage.success(GroupMemberNew1);
    }
    //删除
    @DeleteMapping("/{taskId}")
    public ResponseMessage delete(@PathVariable Integer taskId){
        groupmemberService.delete(taskId);
        return ResponseMessage.success();
    }

}