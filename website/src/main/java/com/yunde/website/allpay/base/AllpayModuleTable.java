package com.yunde.website.allpay.base;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.ext.kit.ModelKit;
import com.jfinal.plugin.activerecord.Model;
import com.yunde.website.allpay.base.base.BaseAllpayBean;

import java.util.Map;

/**
 * Created by iisky on 2017/6/16 0016.
 */
public class AllpayModuleTable{
    private Class<? extends IAllpayModule> moduleClass;
    private String table;

    public AllpayModuleTable(IAllpayModule moudle, String table){
        this.moduleClass = moudle.getClass();
        this.table = table;
    }

    protected AllpayServiceBind getAllpayServiceBind(){
        return _AllpayModuleBuilder.get(moduleClass);
    }
    public String version(){
        if(getAllpayServiceBind() == null){
            return null;
        }
        return getAllpayServiceBind().version();
    }

    public String service(){
        if(getAllpayServiceBind() == null){
            return null;
        }
        return getAllpayServiceBind().service();
    }

    public String table(){
        return table;
    }

    private Map<String,Object> getBeanMap(BaseAllpayBean bean){
        Map<String,Object> map = bean.toMap();
        for(Map.Entry<String,Object> entry : map.entrySet()){
            if(entry.getValue() == null && bean._getAttrs().get(entry.getKey()) != null){
                entry.setValue(bean._getAttrs().get(entry.getKey()));
            }
        }
        return map;
    }

    public <M extends BaseAllpayBean> AllpayResult add(M bean){
        return add(getBeanMap(bean));
    }

    public AllpayResult add(Map<String,Object> map){
        return _Allpay.request(_Allpay.ModuleBusType.add.getUrl(version(), service(), table), map);
    }

    @Deprecated
    public AllpayResult addModel(Model<?> model){
        return add(ModelKit.toMap(model));
    }

    public <M extends BaseAllpayBean> AllpayResult updateById(M bean){
        return updateById(getBeanMap(bean));
    }

    public AllpayResult updateById(Map<String,Object> map){
        return _Allpay.request(_Allpay.ModuleBusType.updateById.getUrl(version(), service(), table), map);
    }

    @Deprecated
    public AllpayResult updateModelById(Model<?> model){
        return updateById(ModelKit.toMap(model));
    }

    public AllpayResult deleteById(Object... id){
        return _Allpay.request(_Allpay.ModuleBusType.deleteById.getUrl(version(), service(), table), new JSONObject(){{
            put("id", id);
        }});
    }

    public AllpayResult query(Map<String,Object> map){
        return query(map, false);
    }
    public AllpayResult query(Map<String,Object> map, boolean isJdbcRead){
        setJdbcReadMap(map, isJdbcRead);
        return _Allpay.request(_Allpay.ModuleBusType.query.getUrl(version(), service(), table), map);
    }

    public AllpayResult queryById(Object id){
        return queryById(id, false);
    }
    public AllpayResult queryById(Object id, boolean isJdbcRead){
        JSONObject map = new JSONObject(){{
            put("id", id);
        }};
        setJdbcReadMap(map, isJdbcRead);
        return _Allpay.request(_Allpay.ModuleBusType.queryById.getUrl(version(), service(), table), map);
    }

    public AllpayResult paginate(int pageNumber, int pageSize, Map<String,Object> map){
        return paginate(pageNumber, pageSize, map, false);
    }
    public AllpayResult paginate(int pageNumber, int pageSize, Map<String,Object> map, boolean isJdbcRead){
        setJdbcReadMap(map, isJdbcRead);
        return _Allpay.request(_Allpay.ModuleBusType.paginate.getUrl(version(), service(), table), new JSONObject(){{
            putAll(map);
            put("pageNumber", pageNumber);
            put("pageSize", pageSize);
        }});
    }

    public AllpayResult count(Map<String,Object> map){
        return count(map, false);
    }
    public AllpayResult count(Map<String,Object> map, boolean isJdbcRead){
        setJdbcReadMap(map, isJdbcRead);
        return _Allpay.request(_Allpay.ModuleBusType.count.getUrl(version(), service(), table), map);
    }

    //尽管这里设置了从只读库获取数据，但是如果module里面没有配置只读库的话，还是从主库获取数据，所以放心使用即可
    private static void setJdbcReadMap(Map<String,Object> map, boolean isJdbcRead){
        if(isJdbcRead){
            map.put("jdbcRead", true);
        }
    }
}
