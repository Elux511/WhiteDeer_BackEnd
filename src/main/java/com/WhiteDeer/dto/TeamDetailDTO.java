package com.WhiteDeer.dto;
import java.util.List;
public class TeamDetailDTO {
    private List<MemberDTO> memberlist;
    private List<TaskDTO> tasklist;

    public List<MemberDTO> getMemberlist() {
        return memberlist;
    }

    public void setMemberlist(List<MemberDTO> memberlist) {
        this.memberlist = memberlist;
    }

    public List<TaskDTO> getTasklist() {
        return tasklist;
    }

    public void setTasklist(List<TaskDTO> tasklist) {
        this.tasklist = tasklist;
    }
}
