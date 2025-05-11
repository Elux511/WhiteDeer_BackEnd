create table users
(
    user_id      bigint auto_increment
        primary key,
    user_name    varchar(20)  not null,
    phone_number varchar(24)  not null,
    password     varchar(24)  not null,
    face         varchar(255) null,
    yes_task_set json         null,
    no_task_set  json         null,
    group_set    json         null,
    constraint users_pk
        unique (user_id)
);

