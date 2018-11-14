package com.yunde.website.controller;

import com.yunde.frame.tools.ResultMsg;

import java.util.List;
import java.util.Map;

/**
 *
 * @author suwy
 * @date 2018/10/16
 */
public abstract class BaseController {


    /**
     * 转成key value格式
     * @param data
     * @return
     */
    protected ResultMsg formateData2CN(Object data, Map<String, String> keys) {
        if (data instanceof List) {
            List list = (List) data;
            int listSize = list.size();
            for (int i=0; i<listSize; i++) {

            }
        }
        return ResultMsg.success(data);
    }
}
