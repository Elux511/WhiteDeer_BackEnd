package com.WhiteDeer.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("找不到ID为 " + userId + " 的用户");
    }
}