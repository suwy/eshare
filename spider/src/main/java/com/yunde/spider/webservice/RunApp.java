package com.yunde.spider.webservice;

/**
 * Created by laisy on 2018/8/13.
 */
public class RunApp {

    public static void main(String[] args) {
        weather();
    }

    public static void weather() {
        WSEntity wsEntity = new WSEntity()
                .setUrl("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl")
                .setAction("http://WebXml.com.cn/getRegionDataset")
                .setNamespace("http://WebXml.com.cn/")
                .setMethod("getRegionDataset");
        WSAbstractFactory wsAbstractFactory = new WSAbstractFactory(wsEntity);
        System.out.println(wsAbstractFactory.getNodeList("Province"));
    }
}
