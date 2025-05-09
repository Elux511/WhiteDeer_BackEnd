package com.WhiteDeer.repository;

import com.WhiteDeer.entity.User;
import com.WhiteDeer.exception.UserNotFoundException;
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
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public UserRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private static final class UserRowMapper implements RowMapper<User> {
        private final ObjectMapper objectMapper;

        public UserRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setPassword(rs.getString("password"));

            try {
                Set<String> groupSet = objectMapper.readValue(
                        rs.getString("group_set"),
                        new TypeReference<Set<String>>() {}
                );
                user.getGroupSet().addAll(groupSet);

                Set<String> yesTaskSet = objectMapper.readValue(
                        rs.getString("yes_task_set"),
                        new TypeReference<Set<String>>() {}
                );
                user.getYesTaskSet().addAll(yesTaskSet);

                Set<String> noTaskSet = objectMapper.readValue(
                        rs.getString("no_task_set"),
                        new TypeReference<Set<String>>() {}
                );
                user.getNoTaskSet().addAll(noTaskSet);
            } catch (Exception e) {
                throw new SQLException("Failed to parse user sets", e);
            }

            return user;
        }
    }

    public User save(User user) {
        String sql;
        if (user.getId() == null) {
            user.setId(java.util.UUID.randomUUID().toString());
            sql = "INSERT INTO users (id, name, phone_number, password, group_set, yes_task_set, no_task_set) " +
                    "VALUES (?, ?, ?, ?, ?::jsonb, ?::jsonb, ?::jsonb)";
        } else {
            sql = "UPDATE users SET name = ?, phone_number = ?, password = ?, " +
                    "group_set = ?::jsonb, yes_task_set = ?::jsonb, no_task_set = ?::jsonb " +
                    "WHERE id = ?";
        }

        try {
            String groupSetJson = objectMapper.writeValueAsString(user.getGroupSet());
            String yesTaskSetJson = objectMapper.writeValueAsString(user.getYesTaskSet());
            String noTaskSetJson = objectMapper.writeValueAsString(user.getNoTaskSet());

            if (user.getId() == null) {
                jdbcTemplate.update(sql,
                        user.getId(), user.getName(), user.getPhoneNumber(),
                        user.getPassword(), groupSetJson, yesTaskSetJson, noTaskSetJson);
            } else {
                jdbcTemplate.update(sql,
                        user.getName(), user.getPhoneNumber(), user.getPassword(),
                        groupSetJson, yesTaskSetJson, noTaskSetJson,
                        user.getId());
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public User findById(String userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(objectMapper), userId);
        } catch (Exception e) {
            throw new UserNotFoundException(userId);
        }
    }

    public List<User> findByName(String name) {
        String sql = "SELECT * FROM users WHERE name LIKE ?";
        return jdbcTemplate.query(sql, new UserRowMapper(objectMapper), "%" + name + "%");
    }

    public void deleteById(String userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void addGroup(String userId, String groupId) {
        String sql = "UPDATE users SET group_set = " +
                "jsonb_set(COALESCE(group_set, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb) " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, groupId, userId);
    }

    public void removeGroup(String userId, String groupId) {
        String sql = "UPDATE users SET group_set = " +
                "group_set - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, groupId, userId);
    }

    public void addYesTask(String userId, String taskId) {
        String sql = "UPDATE users SET yes_task_set = " +
                "jsonb_set(COALESCE(yes_task_set, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb), " +
                "no_task_set = no_task_set - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, taskId, taskId, userId);
    }

    public void addNoTask(String userId, String taskId) {
        String sql = "UPDATE users SET no_task_set = " +
                "jsonb_set(COALESCE(no_task_set, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb), " +
                "yes_task_set = yes_task_set - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, taskId, taskId, userId);
    }
}