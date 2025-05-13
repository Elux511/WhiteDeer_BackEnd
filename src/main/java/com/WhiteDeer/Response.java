package com.WhiteDeer;

import java.util.Map;

public class Response <T>{
    private T data;
    private int state;

    //基础成功相应模板
    public static <K> Response<K> newSuccess(int state,K data) {
        Response<K> response = new Response<K>();
        response.setState(state);
        response.setData(data);
        return response;
    }

    //基础响应失败模板
    public static <K> Response<K> newFailed(int state,K data) {
        Response<K> response = new Response<K>();
        response.setState(state);
        response.setData(data);
        return response;
    }

    //基础成功响应码模板
    public static Response<Void> newState(int state) {
        Response<Void> response = new Response<>();
        response.setState(state);
        return response;
    }

    //Getter和Setter
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
