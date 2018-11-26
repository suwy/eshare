package com.yunde.website.allpay.base.plugin.allpay;

import com.yunde.website.allpay.base.base.BaseAllpayBean;

import java.util.Map;

/**
 * Created by iisky on 2017/6/24 0024.
 */
public abstract class AbsAllpayModule implements IAllpayModule {

    protected final AllpayServiceBind getAllpayModuleBind(){
        AllpayServiceBind allpayServiceBind = _AllpayModuleBuilder.get(this.getClass());
        if(allpayServiceBind == null){
            throw new IllegalArgumentException(
                    String.format("%s未定义@AllpayServiceBind，或者AllpayModulePlugin未开启", this.getClass().getName()));
        }
        return allpayServiceBind;
    }

    @Override
    public String version() {
        return getAllpayModuleBind().version();
    }

    @Override
    public String service() {
        return getAllpayModuleBind().service();
    }

    public final <Bean extends BaseAllpayBean> AllpayResult request(Enum<?> business, Bean bean){
        return request(business, bean.toMap());
    }

    public final AllpayResult request(Enum<?> business, Map<String, Object> params){
        return _Allpay.request(String.format(_Allpay.allpay_business_url_format, version(), service(), business.name()), params);
    }
}
