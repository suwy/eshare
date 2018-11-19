package com.yunde.website.allpay.base.plugin.allpay;

import com.alibaba.fastjson.JSONObject;
import com.allpaycloud.base.cloud.plugin.graylog.GraylogKit;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.JsonKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iisky on 2017/6/24 0024.
 */
public class _Allpay {
    private static String apiHost;
    final static String allpay_business_url_format = "/%s/%s/%s";

    public static void setApiHost(String apiHost){
        _Allpay.apiHost = apiHost;
    }

    public enum ModuleBusType {
        add("add%sBus"),
        count("count%sBus"),
        deleteById("delete%sByIdBus"),
        paginate("paginate%sBus"),
        query("query%sBus"),
        queryById("query%sByIdBus"),
        updateById("update%sByIdBus"),;

        private String businessNameFormat;

        ModuleBusType(String businessNameFormat) {
            this.businessNameFormat = businessNameFormat;
        }

        public String getUrl(String version, String service, String table){
            return String.format(allpay_business_url_format, version, service, String.format(this.businessNameFormat, table));
        }
    }

    public static AllpayResult request(String url, Map<String,Object> params){
        if (params == null) {
            params = new HashMap<String, Object>();
        }

        long startTime = System.currentTimeMillis();
        String data = JsonKit.toJson(params);
        String resultStr = post(url, data);

        AllpayResult allpayResult = AllpayResult.create(resultStr);

        GraylogKit.info(new JSONObject(){{
            put("url", url);
            put("input", data);
            put("output", resultStr);
            put("cost", System.currentTimeMillis()-startTime);
            put("succeed", allpayResult.isSucceed());
            put("errorMsg", allpayResult.getErrorMsg());
            put("msgId", allpayResult.get("$msgId"));
            put("type", "api");
        }});

        return allpayResult;
    }

    public static String post(String url, String data){
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=utf-8");

        try{
            String resultStr = HttpKit.post(apiHost.concat(url), data, headers);
            return resultStr;
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
