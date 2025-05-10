package com.WhiteDeer.entity;

import java.util.Random;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name="sms_codes")
@Data

public class SmsCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String code;
    private LocalDateTime expireTime;
    private boolean used = false;
}