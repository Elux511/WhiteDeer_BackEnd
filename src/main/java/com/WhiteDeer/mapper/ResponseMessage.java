package com.WhiteDeer.mapper;

public class ResponseMessage <T>{
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;
    private String message;
    private T data;
    public ResponseMessage(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
  //成功请求
    public static <T> ResponseMessage<T>success(T data){
        return new ResponseMessage<T>(200, "success", data);

    }
    //成功请求（无参数）
    public static <T> ResponseMessage<T>success(){
        return new ResponseMessage<T>(200, "success", data);

    }
}
