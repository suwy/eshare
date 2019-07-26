package com.yunde.website.annotation;

import java.lang.annotation.*;

/**
 * @author laisy
 * @date 2019/7/26
 * @description 日志变量
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogParam {
    String value() default "";
}
