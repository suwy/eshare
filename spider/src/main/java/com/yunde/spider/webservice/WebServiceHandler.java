package com.yunde.spider.webservice;

import com.yunde.frame.tools.ResultMsg;
import com.yunde.frame.tools.StringKit;
import com.yunde.frame.log.YundeLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by laisy on 2018/8/13.
 */
public class WebServiceHandler implements IWebServiceHandler {

    @Override
    public ResultMsg send(WebServiceEntity entity) {
        Method m ;
        ResultMsg response;
        String function = "send";
        try {
            String className = StringKit.uppercase(entity.getRequestTypeEnum().getName().toLowerCase());
            Class<?>  cls = Class.forName("com.yunde.spider.webservice."+className);
            m = cls.getDeclaredMethod(function, WebServiceEntity.class);
            YundeLog.info("请求参数" +entity.toString());
            response = (ResultMsg) m.invoke(cls.newInstance(), entity);
            return response;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
