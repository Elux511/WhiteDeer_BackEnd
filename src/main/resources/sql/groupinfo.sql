CREATE TABLE groupinfo (
                           group_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                           group_name         VARCHAR(20) NOT NULL,
                           group_introduction VARCHAR(255),
                           member_list        JSON,
                           yes_task_set       JSON,
                           no_task_set        JSON,
                           creator_id         BIGINT,
                           max_member         INT DEFAULT 0,
                           create_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           /*CONSTRAINT groupinfo_pk_2 UNIQUE (group_id),--
                           CONSTRAINT groupinfo_users_user_id_fk FOREIGN KEY (creator_id) REFERENCES users(user_id)*/
);
ALTER TABLE groupinfo AUTO_INCREMENT = 10000000;