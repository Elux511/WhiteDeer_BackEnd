create table users
(
    user_id          bigint auto_increment
        primary key,
    user_name        varchar(20) null,
    phone_number     varchar(20) null,
    password         varchar(32) null,
    face             blob        null,
    yes_task_set     json        null,
    no_task_set      json        null,
    create_group_set json        null,
    join_group_set   json        null,
    constraint users_pk
        unique (user_id)
);

describe users;