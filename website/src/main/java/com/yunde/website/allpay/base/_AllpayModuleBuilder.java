package com.yunde.website.allpay.base;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.ext.kit.ClassSearcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iisky on 2017/6/24 0024.
 */
public class _AllpayModuleBuilder {
    private static Map<Class<? extends IAllpayModule>, AllpayServiceBind> serviceBindMap;
    private static Map<String, Class<? extends IAllpayModule>> serviceMap;

    private static Map<String, _AllpaySelectBean> selectBeanMap;

    public static void add(Class<? extends IAllpayModule> moduleClass){
        AllpayServiceBind allpayServiceBind = moduleClass.getAnnotation(AllpayServiceBind.class);
        if(allpayServiceBind == null){
            throw new IllegalArgumentException(String.format("%s未注解@AllpayServiceBind", moduleClass));
        }
        add(moduleClass, allpayServiceBind);
    }

    public static void add(Class<? extends IAllpayModule> moduleClass, AllpayServiceBind allpayServiceBind){
        if(serviceBindMap == null){
            serviceBindMap = new HashMap<Class<? extends IAllpayModule>, AllpayServiceBind>();
            serviceMap = new HashMap<String, Class<? extends IAllpayModule>>();
        }
        if(serviceMap.containsKey(allpayServiceBind.service())){
            throw new IllegalArgumentException(
                    String.format("%s已经被定义在%s上，不能重复定义", allpayServiceBind.service(), serviceMap.get(allpayServiceBind.service()).getName()));
        }

        serviceBindMap.put(moduleClass, allpayServiceBind);
        serviceMap.put(allpayServiceBind.service(), moduleClass);
    }

    public static void addSelectBean(String alias, AllpayModuleTable table, String objectKey, JSONObject params, String[] attrs){
        if(selectBeanMap == null){
            selectBeanMap = new HashMap<String,_AllpaySelectBean>();
        }
        if(selectBeanMap.containsKey(alias)){
            throw new IllegalArgumentException(String.format("%s已经定义，不能重复定义", alias));
        }
        _AllpaySelectBean selectBean = new _AllpaySelectBean(alias, objectKey, null, table, params);
        selectBean.setAttrs(attrs);

        selectBeanMap.put(alias, selectBean);
    }

    public static AllpayServiceBind get(Class<? extends IAllpayModule> moduleClass){
        if(serviceBindMap == null){
            return null;
        }
        return serviceBindMap.get(moduleClass);
    }

    public static _AllpaySelectBean getSelectBean(String alias){
        if(selectBeanMap == null){
            return null;
        }
        return selectBeanMap.get(alias);
    }

    public static void loadModuleConfig(boolean inLib){
        //扫描注解
        List<Class<? extends IAllpayModule>> modules = ClassSearcher.of(IAllpayModule.class).includeAllJarsInLib(inLib).search();
        AllpayServiceBind allpayServiceBind;
        for(Class<? extends IAllpayModule> moduleClass : modules){
            allpayServiceBind = moduleClass.getAnnotation(AllpayServiceBind.class);
            if(allpayServiceBind == null){
                continue;
            }
            add(moduleClass, allpayServiceBind);
        }
    }
}
