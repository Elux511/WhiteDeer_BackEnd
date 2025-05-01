package com.it;

import java.time.LocalTime;

public class GroupMember {
    enum Type{
        manager,
        member
    };

    Group group;
    User user;
    Type member_type;
    LocalTime join_time;
}
