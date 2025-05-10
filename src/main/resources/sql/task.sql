create table task
(
    id                 varchar(8)   not null
        primary key,
    name               varchar(255) null,
    groupId            varchar(255) null,
    beginTime          time         null,
    endTime            time         null,
    method             varchar(50)  null,
    completedUserIds   text         null,
    uncompletedUserIds text         null
);

