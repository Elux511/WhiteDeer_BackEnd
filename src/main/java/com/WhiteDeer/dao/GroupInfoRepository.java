package com.WhiteDeer.dao;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface GroupInfoRepository extends JpaRepository<GroupInfo,Long> {

    List<GroupInfo> findByGroupNameContaining(String name);

    List<GroupInfo> findByCreateTimeBetween(LocalDateTime begin, LocalDateTime end);

    List<GroupInfo> findByCreateTimeAfter(LocalDateTime begin);

    List<GroupInfo> findByCreateTimeBefore(LocalDateTime end);

    @Modifying
    @Transactional
    @Query(value = "UPDATE groupinfo SET member_list = JSON_REMOVE(member_list, JSON_UNQUOTE(JSON_SEARCH(member_list, 'one', :userId))) " +
            "WHERE JSON_SEARCH(member_list, 'one', :userId) IS NOT NULL",
            nativeQuery = true)
    void removeUserFromAllGroups(@Param("userId") Long userId);
}
