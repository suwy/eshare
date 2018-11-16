package com.yunde.website.controller;

import com.yunde.frame.tools.Const;
import com.yunde.frame.tools.ResultMsg;

import java.util.ArrayList;
import java.util.HashMap;
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
    protected ResultMsg formateData(Object data, Map<String, String> keys) {
        if (data instanceof List) {
            List result = new ArrayList();
            List list = (List) data;
            list.forEach(item -> {
                Map inner = (Map) item;
                keys.forEach((k,v) -> {
                    result.add(new HashMap() {{
                        put(Const.KEY, v);
                        put(Const.VALUE, inner.get(k));
                    }});
                });
            });
            return ResultMsg.success(result);
        }
        return null;
    }
}
