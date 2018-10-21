package com.yunde.spider;

import com.yunde.frame.tools.ResultMsg;

/**
 * Created by laisy on 2018/8/14.
 */
public interface IBaseHandler<T> {

    ResultMsg send(T params);
}
