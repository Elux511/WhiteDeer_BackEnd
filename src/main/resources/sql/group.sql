create table `group`
(
    group_id     varchar(8)  not null
        primary key,
    group_name   varchar(20) not null,
    creator_id   varchar(8)  not null,
    member_list   text        null,
    yes_task_set   text        null,
    no_task_Set    text        null,
    group_introduction text        null
);

