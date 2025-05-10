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
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public TaskRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 任务行映射器（内部类）
     */
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
                        rs.getString("completed_user_ids"),
                        new TypeReference<>() {}
                );
                task.getCompletedUserIds().addAll(completedUsers);

                Set<String> uncompletedUsers = objectMapper.readValue(
                        rs.getString("uncompleted_user_ids"),
                        new TypeReference<>() {}
                );
                task.getUncompletedUserIds().addAll(uncompletedUsers);
            } catch (Exception e) {
                throw new SQLException("Failed to parse user sets", e);
            }
            return task;
        }
    }

    /**
     * 保存任务（自动生成ID）
     * @param task 任务实体（不需要包含ID）
     * @return 保存后的任务实体（包含生成的ID）
     */
    public Task save(Task task) {
        // 强制生成新ID（确保前端传入的ID不会被使用）
        String sql;
        System.out.println(task.toString());
        if(task.getId() == null) {
            task.setId(UUID.randomUUID().toString());
            System.out.println(task.toString());
            sql = "INSERT INTO task (task_id, task_name, group_id, begin_time, end_time, method, " +
                    "completed_user_ids, uncompleted_user_ids) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }else{
            sql="UPDATE task SET task_name=? ,group_id=?,begin_time=?,end_time=?,method=?,"+
                    "completed_user_ids=?,uncompleted_user_ids=?";
        }





        try {
            String completedUsersJson = objectMapper.writeValueAsString(task.getCompletedUserIds());
            String uncompletedUsersJson = objectMapper.writeValueAsString(task.getUncompletedUserIds());

            jdbcTemplate.update(sql,
                    task.getId(),
                    task.getName(),
                    task.getGroupId(),
                    task.getBeginTime(),
                    task.getEndTime(),
                    task.getMethod().name(),
                    completedUsersJson,
                    uncompletedUsersJson);

            return task;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save task", e);
        }
    }

    /**
     * 更新任务（必须包含有效ID）
     * @param task 任务实体（必须包含ID）
     * @return 更新后的任务实体
     */
    public Task update(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task ID cannot be null for update");
        }

        String sql = "UPDATE tasks SET name = ?, group_id = ?, begin_time = ?, end_time = ?, " +
                "method = ?, completed_user_ids = ?::jsonb, uncompleted_user_ids = ?::jsonb " +
                "WHERE id = ?";

        try {
            String completedUsersJson = objectMapper.writeValueAsString(task.getCompletedUserIds());
            String uncompletedUsersJson = objectMapper.writeValueAsString(task.getUncompletedUserIds());

            jdbcTemplate.update(sql,
                    task.getName(),
                    task.getGroupId(),
                    task.getBeginTime(),
                    task.getEndTime(),
                    task.getMethod().name(),
                    completedUsersJson,
                    uncompletedUsersJson,
                    task.getId());

            return task;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update task", e);
        }
    }

    /**
     * 根据ID查找任务
     * @param taskId 任务ID
     * @return 任务实体
     * @throws TaskNotFoundException 任务不存在时抛出
     */
    public Task findById(String taskId) {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new TaskRowMapper(objectMapper), taskId);
        } catch (Exception e) {
            throw new TaskNotFoundException(taskId);
        }
    }

    /**
     * 根据组ID查找任务列表
     * @param groupId 组ID
     * @return 该组的任务列表
     */
    public List<Task> findByGroupId(String groupId) {
        String sql = "SELECT * FROM tasks WHERE group_id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(objectMapper), groupId);
    }

    /**
     * 删除指定任务
     * @param taskId 任务ID
     */
    public void deleteById(String taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    // ...（其他状态管理方法与之前保持一致）...
    public void addCompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET " +
                "completed_user_ids = jsonb_set(" +
                "COALESCE(completed_user_ids, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb), " +
                "uncompleted_user_ids = uncompleted_user_ids - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, userId, taskId);
    }

    /**
     * 添加未完成用户
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    public void addUncompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET " +
                "uncompleted_user_ids = jsonb_set(" +
                "COALESCE(uncompleted_user_ids, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb), " +
                "completed_user_ids = completed_user_ids - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, userId, taskId);
    }

    /**
     * 移除已完成用户
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    public void removeCompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET " +
                "completed_user_ids = completed_user_ids - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, taskId);
    }

    /**
     * 移除未完成用户
     * @param taskId 任务ID
     * @param userId 用户ID
     */
    public void removeUncompletedUser(String taskId, String userId) {
        String sql = "UPDATE tasks SET " +
                "uncompleted_user_ids = uncompleted_user_ids - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, taskId);
    }


}