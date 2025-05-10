create table `group`
(
    group_id     varchar(8)  not null   primary key,
    group_name   varchar(20) not null,
    creator_id   varchar(8)  not null,
    memberList   text        null,
    yesTaskSet   text        null,
    noTaskSet    text        null,
    introduction text        null
);

DELIMITER $$
CREATE TRIGGER before_insert_group_table
    BEFORE INSERT ON group_table
    FOR EACH ROW
BEGIN
    IF NEW.id IS NULL THEN
        SET NEW.id = UUID();
END IF;
END$$
DELIMITER ;