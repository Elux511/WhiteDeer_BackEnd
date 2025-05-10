package com.WhiteDeer.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String groupId) {
        super("找不到ID为 " + groupId + " 的群组");
    }
}