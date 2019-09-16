package com.yunde.frame.tools;

import java.io.Serializable;

/**
 * @author: suwy
 * @date: 2018/11/12
 * @decription:
 */
public class ResultMsg<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    ResultMsg(String code, T object) {
        this.code = code;
        this.data = object;
    }

    ResultMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultMsg failure(String message) {
        return new ResultMsg(Const.CODE_FAILURE, message);
    }

    public static <T> ResultMsg success(T data) {
        return new ResultMsg(Const.CODE_SUCCESS, data);
    }

    public String getCode() {
        return code;
    }

    public ResultMsg<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultMsg<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultMsg<T> setData(T data) {
        this.data = data;
        return this;
    }
}
