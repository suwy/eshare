package com.yunde.test;

import com.yunde.frame.tools.ResultMsg;
import com.yunde.frame.log.YundeLog;
import com.yunde.spider.RequestTypeEnum;
import com.yunde.spider.webservice.WebServiceEntity;
import com.yunde.spider.webservice.WebServiceHandler;
import org.junit.Test;

/**
 * Created by laisy on 2018/10/15.
 */
public class TestWebservice {

    @Test
    public void region() {
        WebServiceEntity webServiceEntity = new WebServiceEntity("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl",
                "http://WebXml.com.cn/getRegionDataset", "http://WebXml.com.cn/", "getRegionDataset",
                "", RequestTypeEnum.AXIS2);
        WebServiceHandler wsHandler = new WebServiceHandler();
        ResultMsg resultMsg = wsHandler.send(webServiceEntity);
        YundeLog.info(resultMsg.getData().toString());
    }

    @Test
    public void student() {
        WebServiceEntity webServiceEntity = new WebServiceEntity("https://zrrk.com.cn:8443/webservice/services/TyjkService?wsdl",
                "process","{\"data\":{\"sfzjh\":\"440602200206181825\"},\"sqxxid\":\"930527d4658023a401658895ed9d1c03\",\"user\":{\"username\":\"禅城区政治生态评估系统接口_教育局学籍查询接口\",\"password\":\"jw@2018\"}}","zrrkjson",
                RequestTypeEnum.CXF);
        WebServiceHandler wsHandler = new WebServiceHandler();
        ResultMsg resultMsg = wsHandler.send(webServiceEntity);
        YundeLog.info(resultMsg.getData().toString());
    }
}
