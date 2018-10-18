package com.yunde.test;

import com.yunde.spider.webservice.WebServiceEntity;
import com.yunde.spider.webservice.WebServiceHandler;
import org.junit.Test;

/**
 * Created by laisy on 2018/10/15.
 */
public class TestWebservice {

    @Test
    public void region() {
        WebServiceEntity webServiceEntity = new WebServiceEntity("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl","http://WebXml.com.cn/getRegionDataset",
                "http://WebXml.com.cn/", "getRegionDataset","");
//        WebServiceHandler wsHandler = new WebServiceHandler(webServiceEntity);
//        System.out.println(wsHandler.getNodeList(wsHandler.sendByAxis2(),"Province"));
    }

    //wether网址用.net生成的wsdl，undefined element declaration 's:schema'
    @Test
    public void region2() {
        WebServiceEntity webServiceEntity = new WebServiceEntity("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl",
                "getRegionDataset","",
                "");
        WebServiceHandler wsHandler = new WebServiceHandler();
        System.out.println(wsHandler.send(webServiceEntity));
    }

    @Test
    public void student() {
        WebServiceEntity webServiceEntity = new WebServiceEntity("https://zrrk.com.cn:8443/webservice/services/TyjkService?wsdl",
                "process","{\"data\":{\"sfzjh\":\"440602200206181825\"},\"sqxxid\":\"930527d4658023a401658895ed9d1c03\",\"user\":{\"username\":\"禅城区政治生态评估系统接口_教育局学籍查询接口\",\"password\":\"jw@2018\"}}","zrrkjson");
        WebServiceHandler wsHandler = new WebServiceHandler();
        System.out.println(wsHandler.sendByCxf(webServiceEntity));
    }

    public static void room() {
        WebServiceEntity webServiceEntity = new WebServiceEntity("https://zrrk.com.cn:8443/webservice/services/TyjkService?wsdl",
                "process","{\"data\":{\"kind\":\"0\",\"qlrmc\":\"\",\"zjh\":\"440621196511162110\"},\"user\":{\"username\":\"禅城区政治生态评估系统接口\",\"password\":\"jw@2018\"},\"sqxxid\":\"930527d46469e47301647cefad033f62\"}",
                "zrrkjson");
        WebServiceHandler wsHandler = new WebServiceHandler();
        System.out.println(wsHandler.sendByCxf(webServiceEntity));
    }
}
