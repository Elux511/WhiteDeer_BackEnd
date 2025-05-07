package com.WhiteDeer;

import java.time.LocalTime;

public class GroupMember {
    public enum Type{
        manager,
        member
    };

    Group group;
    User user;
    Type member_type;
    LocalTime join_time;

    public Group getGroup() {
        return group;
    }
    public User getUser() {
        return user;
    }
    public Type getMember_type() {
        return member_type;
    }
    public LocalTime getJoin_time() {
        return join_time;
    }
    public void setGroup(Group group){
        this.group = group;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setMember_type(Type member_type){
        this.member_type = member_type;
    }
    public void setJoin_time(LocalTime join_time){
        this.join_time = join_time;
    }
}
