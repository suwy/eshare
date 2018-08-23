package com.yunde.spider.webservice;

/**
 * Created by laisy on 2018/8/13.
 */
public class RunApp {

    public static void main(String[] args) {
        region();
//        student();
//        room();
//        region2();
    }

    public static void region() {
        WSEntity wsEntity = new WSEntity("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl","http://WebXml.com.cn/getRegionDataset",
                "http://WebXml.com.cn/", "getRegionDataset","");
        WSHandler wsHandler = new WSHandler(wsEntity);
        System.out.println(wsHandler.getNodeList(wsHandler.send(),"Province"));
    }

    //wether网址用.net生成的wsdl，undefined element declaration 's:schema'
    public static void region2() {
        WSEntity wsEntity = new WSEntity("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl",
                "getRegionDataset","",
                "");
        WSHandler wsHandler = new WSHandler(wsEntity);
        System.out.println(wsHandler.sendByCxf());
    }

    public static void student() {
        WSEntity wsEntity = new WSEntity("http://19.133.15.8:10009/service/stu_cj?wsdl","http://web_pro/Stu_cj/Jj_strRequest",
                "http://web_pro/", "Jj_str","");
        WSHandler wsHandler = new WSHandler(wsEntity);
        wsHandler.send();
    }

    public static void room() {
        WSEntity wsEntity = new WSEntity("https://zrrk.com.cn:8443/webservice/services/TyjkService?wsdl",
                 "process","{\"data\":{\"kind\":\"0\",\"qlrmc\":\"\",\"zjh\":\"440621196511162110\"},\"user\":{\"username\":\"禅城区政治生态评估系统接口\",\"password\":\"jw@2018\"},\"sqxxid\":\"930527d46469e47301647cefad033f62\"}",
                "zrrkjson");
        WSHandler wsHandler = new WSHandler(wsEntity);
        System.out.println(wsHandler.sendByCxf());
    }
}
