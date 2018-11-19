package com.yunde.website.allpay.base.plugin.allpay;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.json.JsonManager;
import com.jfinal.plugin.IPlugin;
import com.yunde.website.allpay.base.json.AllpayJsonFactory;

/**
 * Created by iisky on 2017/6/24 0024.
 */
public class AllpayModulePlugin implements IPlugin {
    private boolean autoLoad = false;
    private boolean inLib = false;
    public AllpayModulePlugin(String apiHost){
        _Allpay.setApiHost(apiHost);
    }

    public void autoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }
    public void inlib(boolean inLib){ this.inLib = inLib; }

    public void add(Class<? extends IAllpayModule> moduleClass){
        _AllpayModuleBuilder.add(moduleClass);
    }

    public void addSelectBean(String alias, AllpayModuleTable table, String objectKey, JSONObject params, String[] attrs){
        _AllpayModuleBuilder.addSelectBean(alias, table, objectKey, params, attrs);
    }

    @Override
    public boolean start() {
        if(autoLoad){
            _AllpayModuleBuilder.loadModuleConfig(inLib);
        }
        JsonManager.me().setDefaultJsonFactory(new AllpayJsonFactory());
        return true;
    }

    @Override
    public boolean stop() {
        return false;
    }
}
