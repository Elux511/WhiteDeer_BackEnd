create table tasks
(
    task_id               bigint auto_increment
        primary key,
    group_id              bigint       not null,
    group_name            varchar(20)  null,
    task_name             varchar(32)  not null,
    begin_time            timestamp    not null,
    end_time              timestamp    not null,
    completed_user_list   json         null,
    uncompleted_user_list json         null,
    type                  varchar(20)  not null,
    description           varchar(255) null,
    latitude              double       null,
    longitude             double       null,
    accuracy              double       null,
    isQRcode              tinyint(1)   null,
    should_count          int          null,
    actual_count          int          null,
    constraint tasks_pk_2
        unique (task_id),
    constraint tasks_groupinfo_group_id_fk
        foreign key (group_id) references groupinfo (group_id)
);

