package com.yunde.website.allpay.common.util;

import com.alibaba.fastjson.JSONObject;
import com.allpaycloud.base.cloud.plugin.allpay.AllpayResult;
import com.allpaycloud.base.cloud.plugin.allpay._Allpay;
import com.allpaycloud.module.api.bean.ModuleDeviceBean;
import com.allpaycloud.module.api.bean.ModulePropAddressBean;

/**
 * 数据中心接口
 *
 * @author hang
 */
public class AllpayStatCenter {
    private static final String setBasicMacDataBus = "/v1.0/statCenterService/setBasicMacDataBus";

    public static AllpayResult setBasicMacDataBus(ModuleDeviceBean device) {
        return _Allpay.request(setBasicMacDataBus, new JSONObject() {{
            put("mac", device.getDeviceMac());
            put("deviceId", device.getId());
            put("timestamp", System.currentTimeMillis() / 1000);
        }});
    }

    private static final String setBasicAddrDataByAddressIdBus = "/v1.0/statCenterService/setBasicAddrDataByAddressIdBus";

    public static AllpayResult setBasicAddrDataByAddressIdBus(ModulePropAddressBean address) {
        return _Allpay.request(setBasicAddrDataByAddressIdBus, new JSONObject() {{
            put("addressId", address.getId());
            put("timestamp", System.currentTimeMillis() / 1000);
        }});
    }

    private static final String setBasicDataByDeviceIdBus = "/v1.0/statCenterService/setBasicDataByDeviceIdBus";

    public static AllpayResult setBasicDataByDeviceIdBus(ModuleDeviceBean device) {
        return _Allpay.request(setBasicDataByDeviceIdBus, new JSONObject() {{
            put("deviceId", device.getId());
            put("timestamp", System.currentTimeMillis() / 1000);
        }});
    }

    private static final String getBasicDataBus = "/v1.0/statCenterService/getBasicDataBus";

    public static AllpayResult getBasicDataBus(ModuleDeviceBean device) {
        return _Allpay.request(getBasicDataBus, new JSONObject() {{
            put("deviceId", device.getId());
            put("timestamp", System.currentTimeMillis() / 1000);
        }});
    }
}


