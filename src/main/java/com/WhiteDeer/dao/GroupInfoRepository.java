package com.WhiteDeer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface GroupInfoRepository extends JpaRepository<GroupInfo,Long> {

    List<GroupInfo> findByGroupNameContaining(String name);

    List<GroupInfo> findByCreateTimeBetween(LocalDateTime begin, LocalDateTime end);
}
