package com.yunde.website.annotation;

import java.lang.annotation.*;

/**
 * @author laisy
 * @date 2019/7/26
 * @description 系统日志注解
 */
@Documented()
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LogLog {
    String pageName() default "";
    String objectName() default "";
    String operationInfo() default "";
}
