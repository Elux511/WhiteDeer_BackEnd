package com.it;

import java.util.Vector;

public class Group {
    String id;
    String name;
    User creator;
    Vector<GroupMember> member_list;
    Vector<String> yes_task_set;
    Vector<String> no_task_set;
    String introduction;//简介暂定为String类型
}
