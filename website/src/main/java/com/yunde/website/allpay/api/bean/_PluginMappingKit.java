package com.yunde.website.allpay.api.bean;

import com.yunde.website.allpay.base.plugin.allpay.AllpayModulePlugin;

/**
 * Generated by Allpay, do not modify this file.
 */
public class _PluginMappingKit {
    public static void mapping(AllpayModulePlugin plugin){
        plugin.add(AlipayPlugin.class);
//        plugin.add(CmbcNetTradePlatformPlugin.class);
//        plugin.add(GizwitsPlugin.class);
//        plugin.add(PushPlugin.class);
//        plugin.add(SwiftpassPlugin.class);
//        plugin.add(WeixinMchPlugin.class);
//        plugin.add(WeixinMpPlugin.class);
//        plugin.add(WeixinThirdPlatformPlugin.class);
        //自定义mapping设置，满足子系统分离需求
        _DiyMappingKit.mapping(plugin);
    }
}
