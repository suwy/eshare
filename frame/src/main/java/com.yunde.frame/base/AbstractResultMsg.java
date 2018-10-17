package com.yunde.frame.base;

import java.io.Serializable;

public class AbstractResultMsg<T> implements Serializable {

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
