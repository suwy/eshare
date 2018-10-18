package com.yunde.spider;

/**
 * Created by laisy on 2018/8/14.
 */
public interface IBaseHandler<T> {

    Object request(T entity);

    Object response();
}
