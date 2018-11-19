package com.yunde.website.allpay.base;

import com.alibaba.fastjson.JSONObject;

import java.util.Set;

/**
 * Created by hang on 2017/9/9 0009.
 */
public class _AllpaySelectBean {
    String alias;
    String objectKey;
    String idKey;
    AllpayModuleTable table;
    JSONObject params;
    String attrs[];
    Set<String> to;

    public _AllpaySelectBean(String alias, String objectKey, String idKey, AllpayModuleTable table, JSONObject params) {
        this.alias = alias;
        this.objectKey = objectKey;
        this.idKey = idKey;
        this.table = table;
        this.params = params;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public AllpayModuleTable getTable() {
        return table;
    }

    public void setTable(AllpayModuleTable table) {
        this.table = table;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public String[] getAttrs() {
        return attrs;
    }

    public void setAttrs(String[] attrs) {
        this.attrs = attrs;
    }

    public Set<String> getTo() {
        return to;
    }

    public void setTo(Set<String> to) {
        this.to = to;
    }

    public AllpayResult request(){
        return table.query(params);
    }
}
