package com.WhiteDeer.repository;

import com.WhiteDeer.entity.Group;
import com.WhiteDeer.entity.GroupMember;
import com.WhiteDeer.exception.GroupNotFoundException;
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
public class GroupRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public GroupRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private static final class GroupRowMapper implements RowMapper<Group> {
        private final ObjectMapper objectMapper;

        public GroupRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group group = new Group();
            group.setId(rs.getString("id"));
            group.setName(rs.getString("name"));
            group.setCreatorId(rs.getString("creator_id"));
            group.setIntroduction(rs.getString("introduction"));

            try {
                Set<GroupMember> memberList = objectMapper.readValue(
                        rs.getString("member_list"),
                        new TypeReference<Set<GroupMember>>() {}
                );
                group.getMemberList().addAll(memberList);

                Set<String> yesTaskSet = objectMapper.readValue(
                        rs.getString("yes_task_set"),
                        new TypeReference<Set<String>>() {}
                );
                group.getYesTaskSet().addAll(yesTaskSet);

                Set<String> noTaskSet = objectMapper.readValue(
                        rs.getString("no_task_set"),
                        new TypeReference<Set<String>>() {}
                );
                group.getNoTaskSet().addAll(noTaskSet);
            } catch (Exception e) {
                throw new SQLException("Failed to parse group data", e);
            }

            return group;
        }
    }

    public Group save(Group group) {
        String sql;
        if (group.getId() == null) {
            group.setId(java.util.UUID.randomUUID().toString());
            sql = "INSERT INTO groups (id, name, creator_id, introduction, member_list, yes_task_set, no_task_set) " +
                    "VALUES (?, ?, ?, ?, ?::jsonb, ?::jsonb, ?::jsonb)";
        } else {
            sql = "UPDATE groups SET name = ?, creator_id = ?, introduction = ?, " +
                    "member_list = ?::jsonb, yes_task_set = ?::jsonb, no_task_set = ?::jsonb " +
                    "WHERE id = ?";
        }

        try {
            String memberListJson = objectMapper.writeValueAsString(group.getMemberList());
            String yesTaskSetJson = objectMapper.writeValueAsString(group.getYesTaskSet());
            String noTaskSetJson = objectMapper.writeValueAsString(group.getNoTaskSet());

            if (group.getId() == null) {
                jdbcTemplate.update(sql,
                        group.getId(), group.getName(), group.getCreatorId(),
                        group.getIntroduction(), memberListJson, yesTaskSetJson, noTaskSetJson);
            } else {
                jdbcTemplate.update(sql,
                        group.getName(), group.getCreatorId(), group.getIntroduction(),
                        memberListJson, yesTaskSetJson, noTaskSetJson,
                        group.getId());
            }
            return group;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save group", e);
        }
    }

    public Group findById(String groupId) {
        String sql = "SELECT * FROM groups WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new GroupRowMapper(objectMapper), groupId);
        } catch (Exception e) {
            throw new GroupNotFoundException(groupId);
        }
    }

    public List<Group> findByName(String name) {
        String sql = "SELECT * FROM groups WHERE name LIKE ?";
        return jdbcTemplate.query(sql, new GroupRowMapper(objectMapper), "%" + name + "%");
    }

    public List<Group> findByCreator(String creatorId) {
        String sql = "SELECT * FROM groups WHERE creator_id = ?";
        return jdbcTemplate.query(sql, new GroupRowMapper(objectMapper), creatorId);
    }

    public void deleteById(String groupId) {
        String sql = "DELETE FROM groups WHERE id = ?";
        jdbcTemplate.update(sql, groupId);
    }

    public void addMember(String groupId, GroupMember member) {
        String sql = "UPDATE groups SET member_list = " +
                "jsonb_set(COALESCE(member_list, '[]'::jsonb), " +
                "ARRAY[?::text], ?::jsonb) " +
                "WHERE id = ?";
        try {
            String memberJson = objectMapper.writeValueAsString(member);
            jdbcTemplate.update(sql, member.getUserId(), memberJson, groupId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add member", e);
        }
    }

    public void removeMember(String groupId, String userId) {
        String sql = "UPDATE groups SET member_list = " +
                "member_list - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, userId, groupId);
    }

    public void addYesTask(String groupId, String taskId) {
        String sql = "UPDATE groups SET yes_task_set = " +
                "jsonb_set(COALESCE(yes_task_set, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb), " +
                "no_task_set = no_task_set - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, taskId, taskId, groupId);
    }

    public void addNoTask(String groupId, String taskId) {
        String sql = "UPDATE groups SET no_task_set = " +
                "jsonb_set(COALESCE(no_task_set, '[]'::jsonb), " +
                "ARRAY[?::text], 'true'::jsonb), " +
                "yes_task_set = yes_task_set - ?::text " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, taskId, taskId, groupId);
    }
}