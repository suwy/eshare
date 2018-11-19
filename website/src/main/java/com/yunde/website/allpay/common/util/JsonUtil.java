package com.yunde.website.allpay.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zgc on 2017/9/26.
 */
public class JsonUtil {

    public JSONArray parseArray(String s) {
        return JSONArray.parseArray(s);
    }

    public JSONObject parseObject(String s) {
        return JSONObject.parseObject(s);
    }
}
