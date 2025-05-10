create table `group`
(
    group_id     varchar(8)  not null
        primary key,
    group_name   varchar(20) not null,
    creator_id   varchar(8)  not null,
    memberList   text        null,
    yesTaskSet   text        null,
    noTaskSet    text        null,
    introduction text        null
);

