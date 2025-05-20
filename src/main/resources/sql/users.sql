create table users
(
    user_id          bigint auto_increment
        primary key,
    user_name        varchar(255) null,
    phone_number     varchar(255) null,
    password         varchar(255) null,
    face             longtext     null,
    yes_task_set     json         null,
    no_task_set      json         null,
    create_group_set json         null,
    join_group_set   json         null
    /*constraint users_pk
        unique (user_id)*/
);
ALTER TABLE users AUTO_INCREMENT = 10000000;