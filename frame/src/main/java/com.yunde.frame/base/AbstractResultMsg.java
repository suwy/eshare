package com.yunde.frame.base;

public class AbstractResultMsg<T> {

    private String code;
    private String message;
    private T data;

    public AbstractResultMsg(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public AbstractResultMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
