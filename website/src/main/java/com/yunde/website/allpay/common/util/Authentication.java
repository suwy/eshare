package com.yunde.website.allpay.common.util;

import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.yunde.website.allpay.common.GlobalConfig;

/**
 * Created by hang on 2017/8/17 0017.
 */
public class Authentication {
    public static String login(Integer propUserId){
        int timestamp = DateKit.unixTimestamp();
        return PropKit.get("allpay_management_url")+"/authenticationLogin/"+HashKit.encryptToAes(propUserId+","+timestamp, GlobalConfig.aes_key);
    }
}
