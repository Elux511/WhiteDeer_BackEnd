package com.WhiteDeer;

import java.util.Vector;

public class Group {
    String id;
    String name;
    User creator;
    Vector<GroupMember> member_list;
    Vector<String> yes_task_set;
    Vector<String> no_task_set;
    String introduction;//简介暂定为String类型

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public User getCreator() {
        return creator;
    }
    public Vector<GroupMember> getMember_list() {
        return member_list;
    }
    public Vector<String> getYes_task_set() {
        return yes_task_set;
    }
    public Vector<String> getNo_task_set() {
        return no_task_set;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setIntroduction(String intro){
        this.introduction = intro;
    }
    public void addMember(GroupMember member){
        member_list.add(member);
    }
    public void deleteMember(GroupMember member){
        member_list.remove(member);
    }
    public void addYes(String task_id){
        yes_task_set.add(task_id);
    }
    public void deleteYes(String task_id){
        yes_task_set.remove(task_id);
    }
    public void addNo(String task_id){
        no_task_set.add(task_id);
    }
    public void deleteNo(String task_id){
        no_task_set.remove(task_id);
    }
}
