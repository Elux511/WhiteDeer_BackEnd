create table user
(
    id  varchar(8)   not null    primary key,
    name        varchar(255) null,
    phoneNumber varchar(20)  null,
    password    varchar(255) null,
    groupSet    text         null,
    yesTaskSet  text         null,
    noTaskSet   text         null
);

DELIMITER $$
CREATE TRIGGER before_insert_user_table
    BEFORE INSERT ON user_table
    FOR EACH ROW
BEGIN
    IF NEW.id IS NULL THEN
        SET NEW.id = UUID();
END IF;
END$$
DELIMITER ;

