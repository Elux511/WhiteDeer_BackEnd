package com.WhiteDeer.dao;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByGroupId(long groupId);

    Optional<Task> findById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tasks SET " +
            "completed_user_list = JSON_REMOVE(completed_user_list, JSON_UNQUOTE(JSON_SEARCH(completed_user_list, 'one', :userId))), " +
            "incomplete_user_list = JSON_REMOVE(incomplete_user_list, JSON_UNQUOTE(JSON_SEARCH(incomplete_user_list, 'one', :userId))) " +
            "WHERE JSON_SEARCH(completed_user_list, 'one', :userId) IS NOT NULL " +
            "OR JSON_SEARCH(incomplete_user_list, 'one', :userId) IS NOT NULL",
            nativeQuery = true)
    void removeUserFromAllTasks(@Param("userId") Long userId);

}
