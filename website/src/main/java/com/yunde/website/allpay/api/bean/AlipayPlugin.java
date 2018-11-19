package com.yunde.website.allpay.api.bean;

import com.yunde.website.allpay.base.plugin.allpay.AbsAllpayModule;
import com.yunde.website.allpay.base.plugin.allpay.AllpayModuleTable;
import com.yunde.website.allpay.base.plugin.allpay.AllpayServiceBind;

/**
 * Generated by Allpay, do not modify this file.
 */
@AllpayServiceBind(service = "alipayPluginService")
public class AlipayPlugin extends AbsAllpayModule {
    public final static AlipayPlugin me = new AlipayPlugin();

    public AllpayModuleTable alipayAuthorizerInfo = new AllpayModuleTable(this, "AlipayAuthorizerInfo");
    public AllpayModuleTable alipayConfig = new AllpayModuleTable(this, "AlipayConfig");

    public enum business{
        accessTokenBus,
        authTokenBus,
        closeOrderBus,
        createPreTradeBus,
        createTradeBus,
        downloadBillBus,
        getSnsUserInfoBus,
        getSnsUserInfoByCodeBus,
        queryOrderBus,
        queryRefundBus,
        queryTransferBus,
        refundBus,
        thirdPlatformAuthorizeBus,
        transferBus,
        wapPayBus,
    }
}