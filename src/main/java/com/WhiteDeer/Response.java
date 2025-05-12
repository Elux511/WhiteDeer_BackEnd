package com.WhiteDeer;

public class Response <T>{
    private T data;
    private boolean success;
    private String error_message;

    public static <K> Response<K> newSuccess(K data) {
        Response<K> response = new Response<K>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static Response<Void> newFail(String error_message) {
        Response<Void> response = new Response<>();
        response.setSuccess(false);
        response.setError_message(error_message);
        return response;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public String getError_message() {
        return error_message;
    }
    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
