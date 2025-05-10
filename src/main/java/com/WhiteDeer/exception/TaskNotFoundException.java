package com.WhiteDeer.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String taskId) {
        super("找不到ID为 " + taskId + " 的任务");
    }
}