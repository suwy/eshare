package com.yunde.website.annotation;

import com.yunde.frame.tools.StringKit;
import org.apache.commons.lang.reflect.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author laisy
 * @date 2019/7/26
 * @description
 */
@Component
@Aspect
public class LogAop {

    private static final Logger logger = LoggerFactory.getLogger(LogAop.class);

    @Pointcut("@annotation(com.yunde.website.annotation.LogLog)")
    public void logAspect(){
    }

    @After("logAspect()")
    public void doAfter(JoinPoint joinPoint){
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            Map<String, String> description = getControllerMethodDescription(joinPoint);
//            String username = (String) subject.getPrincipal();
//            if (null != username) {
//                String ip = subject.getPrincipals().fromRealm("IP").toString();
//                logClient.addPageOperationInfo(username, description.get("pageName"), description.get("objectName"), description.get("operationInfo"), ip);
//            }
//        }catch (Exception e){
//            logger.error(StringKit.collectErrorStackMsg(e));
//        }
    }

    private Map<String, String> getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        /*定位方法所需参数*/
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Class serviceClass = Class.forName(targetName);
        int joinPointLength = joinPoint.getArgs().length;
        Class<?>[] paramTypes = new Class<?>[joinPointLength];
        Object[] paramValue = new Object[joinPointLength];
        int i = 0;
        for (Object joinPonitArg : joinPoint.getArgs()) {
            if (null != joinPonitArg) {
                paramTypes[i] = joinPonitArg.getClass();
                paramValue[i] = joinPonitArg;
                i++;
            }
        }
        Map<String, String> result = new HashMap<>();
        Map paramMap = new HashMap();
        try {
            /*获取方法体*/
            Method method = MethodUtils.getMatchingAccessibleMethod(serviceClass, methodName, paramTypes);
            LogLog log = method.getAnnotation(LogLog.class);
            Annotation[][] paramAnns = method.getParameterAnnotations();
            for(int j=0; j<joinPointLength; j++) {
                if (LogParam.class.isInstance(paramAnns[j][0])) {
                    LogParam logParma = (LogParam) paramAnns[j][0];
                    paramMap.put(logParma.value(), paramValue[j]);
                }
            }
            /*获取访问模块和访问操作*/
            if(log!=null){
                result.put("pageName" ,parseParam(log.pageName(),paramMap));
                result.put("objectName", parseParam(log.objectName(),paramMap));
                result.put("operationInfo", parseParam(log.operationInfo(),paramMap));
            }
        }catch (Exception e){
            logger.error(StringKit.collectErrorStackMsg(e));
        }
        return result;
    }

    private String parseParam(String value, Map<String, Object> paramMap) {
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            String keyName = String.format("{%s}", entry.getKey());
            if(value.contains(keyName)) {
                return value.replace(keyName, entry.getValue().toString());
            }
        }
        return value;
    }
}