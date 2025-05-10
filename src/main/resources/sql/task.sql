create table task
(
    task_id                 varchar(8)   not null
        primary key,
    task_name              varchar(255) null,
    group_id            varchar(255) null,
    begin_time          time         null,
    end_time            time         null,
    method             varchar(50)  null,
    completed_user_ids   text         null,
    uncompleted_user_ids text         null
);

