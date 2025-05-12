package com.WhiteDeer;

public class Response <T>{
    private T data;
    private int state;


    public static <K> Response<K> newSuccess(K data) {
        Response<K> response = new Response<K>();
        response.setState(1);
        response.setData(data);
        return response;
    }
    public static <K> Response<K> loginSuccess(K data) {
        Response<K> response = new Response<K>();
        response.setState(1);
        response.setData(data);
        return response;
    }
    public static <K> Response<K> loginFailed(K data) {
        Response<K> response = new Response<K>();
        response.setState(2);
        response.setData(data);
        return response;
    }

    public static <K> Response<K> passwordFailed(K data) {
        Response<K> response = new Response<K>();
        response.setState(3);
        response.setData(data);
        return response;
    }
    public static Response<Void> newstate(int state) {
        Response<Void> response = new Response<>();
        response.setState(state);
        return response;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
}
