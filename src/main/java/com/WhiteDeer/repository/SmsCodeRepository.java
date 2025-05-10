package com.WhiteDeer.repository;

import com.WhiteDeer.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    Optional<SmsCode> findTopByUser_IdAndUsedOrderByExpireTimeDesc(Long userId, boolean used);
}