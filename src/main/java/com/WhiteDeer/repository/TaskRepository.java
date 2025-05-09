package com.WhiteDeer.repository;

import com.WhiteDeer.entity.Task;
import com.WhiteDeer.exception.TaskNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public TaskRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private static final class TaskRowMapper implements RowMapper<Task> {
        private final ObjectMapper objectMapper;

        public TaskRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getString("id"));
            task.setName(rs.getString("name"));
            task.setGroupId(rs.getString("group_id"));
            task.setBeginTime(rs.getTime("begin_time").toLocalTime());
            task.setEndTime(rs.getTime("end_time").toLocalTime());
            task.setMethod(Task.CheckInMethod.valueOf(rs.getString("method")));

            try {
                Set<String> completedUsers = objectMapper.readValue(
                        rs.getString("completed_users"),
                        new TypeReference<Set<String>>() {}
                );
                task.getCompletedUserIds().addAll(completedUsers);

                Set<String> uncompletedUsers = objectMapper.readValue(
                        rs.getString("uncompleted_users"),
                        new TypeReference<Set<String>>() {}
                );
                task.getUncompletedUserIds().addAll(uncompletedUsers);
            } catch (Exception e) {
                throw new SQLException("Failed to parse user sets", e);
            }

            return task;
        }
    }

    public Task save(Task task) {
        String sql;
        if (task.getId() == null) {
            task.setId(java.util.UUID.randomUUID().toString());
            sql = "INSERT INTO tasks (id, name, group_id, begin_time, end_time, method, completed_users, uncompleted_users) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?::jsonb, ?::jsonb)";
        } else {
            sql = "UPDATE tasks SET name = ?, group_id = ?, begin_time = ?, " +
                    "end_time = ?, method = ?, completed_users = ?::jsonb, uncompleted_users = ?::jsonb " +
                    "WHERE id = ?";
        }

        try {
            String completedUsersJson = objectMapper.writeValueAsString(task.getCompletedUserIds());
            String uncompletedUsersJson = objectMapper.writeValueAsString(task.getUncompletedUserIds());

            if (task.getId() == null) {
                jdbcTemplate.update(sql,
                        task.getId(), task.getName(), task.getGroupId(),
                        task.getBeginTime(), task.getEndTime(), task.getMethod().toString(),
                        completedUsersJson, uncompletedUsersJson);
            } else {
                jdbcTemplate.update(sql,
                        task.getName(), task.getGroupId(),
                        task.getBeginTime(), task.getEndTime(), task.getMethod().toString(),
                        completedUsersJson, uncompletedUsersJson,
                        task.getId());
            }
            return task;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save task", e);
        }
    }

    public Task findById(String taskId) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new TaskRowMapper(objectMapper), taskId);
        } catch (Exception e) {
            throw new TaskNotFoundException(taskId);
        }
    }

    public List<Task> findByGroupId(String groupId) {
        String sql = "SELECT * FROM tasks WHERE group_id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(objectMapper), groupId);
    }

    public void deleteById(String taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    public void addCompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET completed_users = " +
                "jsonb_set(COALESCE(completed_users, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb) " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, taskId);
    }

    public void removeCompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET completed_users = " +
                "completed_users - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, taskId);
    }

    public void addUncompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET uncompleted_users = " +
                "jsonb_set(COALESCE(uncompleted_users, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb) " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, taskId);
    }

    public void removeUncompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET uncompleted_users = " +
                "uncompleted_users - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, taskId);
    }
}