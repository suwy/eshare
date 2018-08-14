package com.yunde.spider.webservice;

/**
 * Created by laisy on 2018/8/13.
 */
public class RunApp {

    public static void main(String[] args) {
        weather();
    }

    public static void weather() {
        WSEntity wsEntity = new WSEntity("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl","http://WebXml.com.cn/getRegionDataset",
                "http://WebXml.com.cn/", "getRegionDataset");
        System.out.println(wsEntity.toString());
        WSHandler wsHandler = new WSHandler(wsEntity);
        wsHandler.send();
        System.out.println(wsHandler.getNodeList("Province"));
    }
}
