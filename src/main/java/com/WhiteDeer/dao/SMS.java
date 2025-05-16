package com.WhiteDeer.dao;

public class SMS {
    String phoneNumber;
    String code;
    int exTime;//有效时间

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getExTime() {
        return exTime;
    }

    public void setExTime(int exTime) {
        this.exTime = exTime;
    }
}
