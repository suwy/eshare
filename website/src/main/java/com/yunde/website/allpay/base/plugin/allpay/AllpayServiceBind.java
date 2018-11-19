package com.yunde.website.allpay.base.plugin.allpay;

import java.lang.annotation.*;

/**
 * Created by iisky on 2017/6/24 0024.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface AllpayServiceBind {
    String version() default "v1.0";
    String service();
}
