create table task
(
    id                  varchar(8)   not null        primary key,
    name               varchar(255) null,
    groupId            varchar(255) null,
    beginTime          time         null,
    endTime            time         null,
    method             varchar(50)  null,
    completedUserIds   text         null,
    uncompletedUserIds text         null
);
DELIMITER $$
CREATE TRIGGER before_insert_task_table
    BEFORE INSERT ON task_table
    FOR EACH ROW
BEGIN
    IF NEW.id IS NULL THEN
        SET NEW.id = UUID();
END IF;
END$$
DELIMITER ;

