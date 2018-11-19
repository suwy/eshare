package com.yunde.website.allpay.common.cfg;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * Created by hang on 2017/9/5 0005.
 */
public class AllpayCfg {
    private final static String fileName = "config.txt";

    private static Prop getProp() {
        return PropKit.getProp(fileName);
    }

    public static String getApiUrl() {
        return getProp().get("allpay_api_url");
    }

    public static String getManagementUrl() {
        return getProp().get("allpay_management_url");
    }

    public static String getQrcodeUrl() {
        return getProp().get("allpay_qrcode_url");
    }

    public static String getAlipayISVAppId() {
        return getProp().get("alipayISVAppId");
    }
}
