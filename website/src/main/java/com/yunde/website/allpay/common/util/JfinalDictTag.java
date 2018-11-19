package com.yunde.website.allpay.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allpaycloud.base.cloud.plugin.allpay.AllpayModuleTable;
import com.allpaycloud.module.api.DictModule;
import com.jfinal.kit.StrKit;

import java.util.LinkedHashMap;

/**
 * Created by hang on 2017/8/28 0028.
 */
public class JfinalDictTag {
    public static final JSONObject SYS = new JSONObject(){{
        put("YESORNO", new JSONObject(){{
            put("y", "是");
            put("n", "否");
            put("1", "是");
            put("0", "否");
        }});
        put("STATE", new JSONObject(){{
            put("y", "开启");
            put("n", "关闭");
        }});
        put("SUCCESS", new JSONObject(){{
            put("y", "成功");
            put("n", "失败");
        }});
    }};

    public static Object getLabelId(String dictId, Object detailId) {
        if(StrKit.isBlank(dictId) || detailId == null){
            return null;
        }
        if(SYS.containsKey(dictId)){
            return detailId;
        }
        if(detailId instanceof String){
            return toNum((String)detailId);
        }
        return detailId;
    }

    // 将字母转换成数字
    private static int toNum(String input) {
        int i = 0;
        for (byte b : input.getBytes()) {
            i+=b;
        }
        return i%10+1;
    }

    public static boolean isSysDict(String dictId){
        return StrKit.isBlank(dictId)?false:dictId.startsWith("SYS.");
    }

    public static JSONObject getSysDict(String dictId){
        String[] args = dictId.split("[.]");
        if(args.length == 2){
            JSONObject dict = SYS.getJSONObject(args[1]);
            return dict == null ? new JSONObject() : dict;
        }
        return new JSONObject();
    }

    public static final LinkedHashMap<String,Object> MENU_TYPE = new LinkedHashMap<String,Object>(){{
        put("default", "控制台");
        put("report", "功能报表");
        put("module_setting", "模块设置");
        put("plugin_setting", "插件设置");
        put("sys_setting", "系统设置");
        put("other", "其他功能");
    }};

    public JSONArray select(String dictId){
        return querySelect(DictModule.me.dictDetail, new LinkedHashMap<String, Object>(){{
            put("dictId", dictId);
            put("state", "y");
        }});
    }
    public JSONArray querySelect(AllpayModuleTable table, LinkedHashMap json){
        return table.query(json).dataToJsonArray();
    }

    public JSONObject info(String dictId, Object detailId){
        return queryInfo(DictModule.me.dictDetail, dictId+"-"+detailId);
    }

    public JSONObject queryInfo(AllpayModuleTable table, Object id){
        if(table == null || id == null || id.toString().endsWith("-")){
            return new JSONObject();
        }
        JSONObject json = table.queryById(id).dataToJson();
        return json==null?new JSONObject():json;
    }
}
