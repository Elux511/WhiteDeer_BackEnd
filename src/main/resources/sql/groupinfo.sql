create table groupinfo
(
    group_id           bigint auto_increment
        primary key,
    group_name         varchar(20)  not null,
    group_introduction varchar(255) null,
    member_list        json         null,
    yes_task_set       json         null,
    no_task_set        json         null,
    creator_id         bigint       null,
    constraint groupinfo_pk_2
        unique (group_id),
    constraint groupinfo_users_user_id_fk
        foreign key (creator_id) references users (user_id)
);

