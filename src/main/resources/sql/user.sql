create table user
(
    id          varchar(8)   not null
        primary key,
    name        varchar(255) null,
    phoneNumber varchar(20)  null,
    password    varchar(255) null,
    groupSet    text         null,
    yesTaskSet  text         null,
    noTaskSet   text         null
);

