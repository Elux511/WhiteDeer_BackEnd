create table tasks
(
    task_id              bigint auto_increment
        primary key,
    group_id             bigint      not null,
    task_name            varchar(32) not null,
    begin_time           timestamp   not null,
    end_time             timestamp   not null,
    completed_user_ids   json        null,
    uncompleted_user_ids json        null,
    method               varchar(20) not null,
    constraint tasks_pk_2
        unique (task_id),
    constraint tasks_groupinfo_group_id_fk
        foreign key (group_id) references groupinfo (group_id)
);

