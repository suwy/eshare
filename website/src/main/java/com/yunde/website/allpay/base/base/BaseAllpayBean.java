package com.yunde.website.allpay.base.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.IBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hang on 2017/6/27 0027.
 */
public class BaseAllpayBean implements IBean {
    @JSONField(serialize=false,deserialize=false)
    private Map<String,Object> attrs = new HashMap<String,Object>();

    @JSONField(serialize=false,deserialize=false)
    public void put(String key, Object value){
        attrs.put(key, value);
    }

    @JSONField(serialize=false,deserialize=false)
    public void putAll(Map<String,Object> map){
        if(map == null){
            return;
        }
        for (Map.Entry<String,Object> entry : map.entrySet()){
            put(entry.getKey(), entry.getValue());
        }
    }

    @JSONField(serialize=false,deserialize=false)
    public void remove(String... keys){
        if(keys == null){
            return;
        }
        for(String key : keys){
            remove(key);
        }
    }

    @JSONField(serialize=false,deserialize=false)
    public void remove(String key){
        if(StrKit.isBlank(key)){
            return;
        }
        attrs.remove(key);
    }

    @JSONField(serialize=false,deserialize=false)
    public <T> T get(String key){
        return (T) attrs.get(key);
    }

    @JSONField(serialize=false,deserialize=false)
    public Map<String,Object> toMap(){
        return toMap(false);
    }

    @JSONField(serialize=false,deserialize=false)
    public Map<String,Object> _getAttrs(){
        return attrs;
    }

    @JSONField(serialize=false,deserialize=false)
    private Map<String,Object> toMap(boolean singleBean){
        Map<String,Object> map = (JSONObject) JSONObject.toJSON(this);
        if(!singleBean){
            for(Map.Entry<String,Object> entry : attrs.entrySet()){
                if(!map.containsKey(entry.getKey()) || entry.getValue() != null){
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return map;
    }
}
