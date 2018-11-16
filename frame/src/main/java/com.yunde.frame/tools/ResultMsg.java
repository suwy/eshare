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
    private T object;

    public ResultMsg(String code, T object) {
        this.code = code;
        this.object = object;
    }

    public ResultMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultMsg failure(Object object) {
        return new ResultMsg(Const.CODE_FAILURE, object);
    }

    public static ResultMsg success(Object object) {
        return new ResultMsg(Const.CODE_SUCCESS, object);
    }

    public <T> T getData() {
        return (T) object;
    }
}
