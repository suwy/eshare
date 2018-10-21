package com.yunde.spider.webservice;

import com.yunde.frame.tools.Const;
import com.yunde.frame.tools.ResultMsg;
import com.yunde.frame.log.YundeLog;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class Cxf implements IWebServiceHandler {
    @Override
    public ResultMsg<String> send(WebServiceEntity entity) {
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            org.apache.cxf.endpoint.Client client = dcf.createClient(entity.getUrl());
            Object[] objects = client.invoke(entity.getMethod(), entity.getParams(), entity.getOthers());
            return new ResultMsg(Const.CODE_SUCCESS, objects[0].toString());
        } catch (Exception e) {
            YundeLog.error("接口错误:"+e.getMessage());
            return ResultMsg.failure(e.getMessage());
        }
    }
}
