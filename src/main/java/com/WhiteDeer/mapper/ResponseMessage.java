package com.WhiteDeer.mapper;

public class ResponseMessage {
    private int code;
    private String message;
    private Object data;

    public static ResponseMessage success() {
        return success(null);
    }

    public static ResponseMessage success(Object data) {
        ResponseMessage response = new ResponseMessage();
        response.setCode(200);
        response.setMessage("操作成功");
        response.setData(data);
        return response;
    }

    public static ResponseMessage error(int code, String message) {
        ResponseMessage response = new ResponseMessage();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}