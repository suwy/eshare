package com.yunde.website.allpay.base;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.ext.kit.ModelKit;
import com.jfinal.ext.kit.RecordKit;
import com.jfinal.interfaces.ISuccCallback;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.yunde.website.allpay.base.base.BaseAllpayBean;

import java.util.*;

public class AllpaySelectKit {
    private Object $object;
    private List<String> $aliasList = new ArrayList<>();//用来排序
    private Map<String,_AllpaySelectBean> $beanMap = new HashMap<>();
    private Map<String,Set> $resultMap = new HashMap<>();

    public static AllpaySelectKit create(Object object){
        AllpaySelectKit kit = new AllpaySelectKit();
        kit.$object = object;
        return kit;
    }

    public AllpaySelectKit select(String alias, String objectKey, String idKey, AllpayModuleTable table, JSONObject params, String[] attrs){
        if($object == null) {
            return this;
        }
        if($aliasList.contains(alias)){
            throw new IllegalArgumentException(String.format("别名:%s已定义,不能重复定义", alias));
        }
        if(StrKit.isBlank(idKey)){
            idKey = "id";
        }
        if($object != null && $object instanceof Collection){
            return select($object, alias, objectKey, idKey, table, params, attrs);
        } else {
            return select(new ArrayList<Object>(){{
                add($object);
            }},alias, objectKey, idKey, table, params, attrs);
        }
    }

    public AllpaySelectKit select(String alias, String objectKey, String idKey, AllpayModuleTable table, JSONObject params){
        return select(alias, objectKey, idKey, table, params, null);
    }

    public AllpaySelectKit select(String alias, String objectKey, AllpayModuleTable table, JSONObject params){
        return select(alias, objectKey, null, table, params);
    }

    public AllpaySelectKit select(String alias, String objectKey, AllpayModuleTable table, String[] attrs){
        return select(alias, objectKey, null, table, null, attrs);
    }

    public AllpaySelectKit select(String alias, String objectKey, AllpayModuleTable table){
        return select(alias, objectKey, null, table, null, null);
    }

    private AllpaySelectKit select(Object $object, String alias, String objectKey, String idKey, AllpayModuleTable table, JSONObject params, String[] attrs){
        if($object == null) {
            return this;
        }
        if(params == null){
            params = new JSONObject();
        }

        if(objectKey.indexOf(".") != -1){
            String[] values = objectKey.split("[.]");
            if(!$aliasList.contains(values[0])){
                throw new IllegalArgumentException(String.format("别名:%s未定义,请先定义", values[0]));
            }
            _AllpaySelectBean allpaySelectBean = $beanMap.get(values[0]);
            if(allpaySelectBean != null){
                if(allpaySelectBean.to == null){
                    allpaySelectBean.to = new HashSet<String>(){{
                        add(alias);
                    }};
                } else {
                    allpaySelectBean.to.add(alias);
                }
            }
            params.put("id", new HashSet<Object>());
            objectKey =  values[1];
            _AllpaySelectBean bean = new _AllpaySelectBean(alias, objectKey, idKey, table, params);
            bean.setAttrs(attrs);
            this.$beanMap.put(alias, bean);
        } else {
            Set<Object> ids = getIdsForObject($object, alias, objectKey, false);
            if(ids.size() > 0){
                params.put("id", ids);

                _AllpaySelectBean bean = new _AllpaySelectBean(alias, objectKey, idKey, table, params);
                bean.setAttrs(attrs);
                this.$beanMap.put(alias, bean);
            }
        }
        $aliasList.add(alias);
        return this;
    }

    private Set<Object> getIdsForObject(Object $object, String alias, String objectKey, boolean isSub){
        Set<Object> ids = new HashSet<>();
        Collection<Object> collection = (Collection<Object>)$object;
        Object id;
        for(Object object : collection) {
            if(object == null){
                continue;
            }

            if(isSub){
                id = toMap(object).get(objectKey);
            } else {
                id = get(object, objectKey);
            }

            if(id != null){
                ids.add(id);
                if($resultMap.containsKey(getAliasIdKey(alias,id))){
                    $resultMap.get(getAliasIdKey(alias,id)).add(object);
                } else {
                    $resultMap.put(getAliasIdKey(alias,id), new HashSet(){{
                        add(object);
                    }});
                }
            }
        }
        return ids;
    }

    public void build(){
        if($object == null) {
            return;
        }
        Map<String,Set<String>> $resultKeyMap = new HashMap<>();
        for(String alias : $aliasList){
            final _AllpaySelectBean bean = $beanMap.get(alias);
            if(bean == null){
                continue;
            }
            bean.request().call(new ISuccCallback<AllpayResult>() {
                @Override
                public AllpayResult callback(AllpayResult result) {
                    boolean hasTo = bean.to != null;
                    for(JSONObject json : result.dataToJsonArray().toJavaList(JSONObject.class)){
                        Object selectIdValue = json.get(bean.idKey);
                        if(selectIdValue != null && $resultMap.containsKey(getAliasIdKey(bean.alias,selectIdValue))){
                            String aliasIdKey = getAliasIdKey(bean.alias,selectIdValue);
                            if(hasTo){
                                for(String toAlias : bean.to){
                                    Object value = json.get($beanMap.get(toAlias).objectKey);
                                    if(value != null) {
                                        String toAliasIdKey = getAliasIdKey($beanMap.get(toAlias).alias,value);
                                        ((Set)$beanMap.get(toAlias).params.get("id")).add(value);
                                        $resultMap.put(toAliasIdKey,$resultMap.get(aliasIdKey));

                                        if($resultKeyMap.containsKey(toAliasIdKey)){
                                            $resultKeyMap.get(toAliasIdKey).add(aliasIdKey);
                                        } else {
                                            $resultKeyMap.put(toAliasIdKey, new HashSet<String>(){{
                                               add(aliasIdKey);
                                            }});
                                        }
                                    }
                                }
                            }
                            if($resultKeyMap.containsKey(aliasIdKey)){
                                for(String key : $resultKeyMap.get(aliasIdKey)){
                                    cnd($resultMap.get(key), bean, selectIdValue, json);
                                }
                            } else{
                                cnd($resultMap.get(aliasIdKey), bean, selectIdValue, json);
                            }
                        }
                    }
                    return result;
                }
            });
        }
        $resultMap = null;
    }

    private static void cnd(Set set,_AllpaySelectBean bean, Object selectIdValue, JSONObject json){
        for(Object idObject : set){
            if(bean.attrs == null){
                put(idObject, bean.alias, json);
            } else {
                put(idObject, bean.alias, new JSONObject(){{
                    put(bean.idKey, selectIdValue);
                    for(String attr : bean.attrs){
                        put(attr, json.get(attr));
                    }
                }});
            }
        }
    }

    private static String getAliasIdKey(String alias,Object idValue){
        return alias+"."+idValue;
    }

    private static void put(Object object, String key, Object value){
        if(object instanceof Map){
            ((Map<String, Object>) object).put(key, value);
        } else if(object instanceof JSONObject) {
            ((JSONObject) object).put(key, value);
        } else if(BaseAllpayBean.class.isAssignableFrom(object.getClass())) {
            ((BaseAllpayBean) object).put(key, value);
        } else if(Model.class.isAssignableFrom(object.getClass())){
            ((Model<?>) object).put(key, value);
        } else if(object instanceof Record){
            ((Record) object).set(key, value);
        } else{
            throw new IllegalArgumentException(String.format("%s类型不支持，暂时只支持Map,JSONObject,BaseAllpayBean,Model和Record类型", object.getClass().getName()));
        }
    }

    private static Object get(Object object, String key){
        Map<String, Object> map = toMap(object);
        if(map == null){
            return null;
        }
        return map.get(key);
    }

    private static Map<String, Object> toMap(Object object){
        if(object instanceof Map){
            return (Map<String, Object>) object;
        } else if(object instanceof JSONObject) {
            return (JSONObject) object;
        } else if(BaseAllpayBean.class.isAssignableFrom(object.getClass())) {
            return ((BaseAllpayBean)object).toMap();
        } else if(Model.class.isAssignableFrom(object.getClass())){
            return ModelKit.toMap((Model<?>)object);
        } else if(object instanceof Record){
            return RecordKit.toMap((Record)object);
        } else{
            throw new IllegalArgumentException(String.format("%s类型不支持，暂时只支持Map,JSONObject,Model和Record类型", object.getClass().getName()));
        }
    }
}
