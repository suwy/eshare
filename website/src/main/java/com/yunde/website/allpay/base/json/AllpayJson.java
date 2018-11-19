package com.yunde.website.allpay.base.json;

import com.jfinal.json.JFinalJson;
import com.yunde.website.allpay.base.base.BaseAllpayBean;

/**
 * Created by hang on 2017/6/30 0030.
 */
public class AllpayJson extends JFinalJson {
    @Override
    protected String otherToJson(Object value, int depth) {
        if(value instanceof BaseAllpayBean){
            return mapToJson(((BaseAllpayBean)value).toMap(), depth);
        }
        return super.otherToJson(value, depth);
    }
}
