package com.yunde.wechat.controller;//package org.fsdcic.dmforcccd.dcc.control.wsdl;
//
//import org.apache.axiom.om.OMAbstractFactory;
//import org.apache.axiom.om.OMElement;
//import org.apache.axiom.om.OMFactory;
//import org.apache.axiom.om.OMNamespace;
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.addressing.EndpointReference;
//import org.apache.axis2.client.Options;
//import org.apache.axis2.client.ServiceClient;
//import org.apache.axis2.rpc.client.RPCServiceClient;
//
//import javax.xml.namespace.QName;
//import java.util.Iterator;
//
///**
// * Created by laisy on 2018/8/10.
// */
//public class StockQuoteClient {
//
//    /**
//     * 方法一： 应用rpc的方式调用 这种方式就等于远程调用， 即通过url定位告诉远程服务器，告知方法名称，参数等， 调用远程服务，得到结果。
//     *  使用 org.apache.axis2.rpc.client.RPCServiceClient类调用WebService
//     *
//     * 【注】：
//     *
//     * 如果被调用的WebService方法有返回值 应使用 invokeBlocking 方法 该方法有三个参数
//     * 第一个参数的类型是QName对象，表示要调用的方法名；
//     * 第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]； 当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}。
//     * 不过要是服务端发布的方法有多个参数值，RPCServiceClient好像是搞不定，这边参数只提供了一个参数数组，也没有参数对应的描述，
//     如：// 定义参数
//     Object[] params = new Object[] { 1432,"","",0,"" };
//     会生成以下类型的报文。
//     <arg0 xmlns="">1432</arg0>
//     <arg1 xmlns=""></arg1>
//     <arg2 xmlns=""></arg2>
//     <arg3 xmlns="">0</arg3>
//     <arg4 xmlns=""></arg4>
//     服务端碰到这类型的报文，参数匹配不上
//     所以在遇到这种情况是可以采用方法二： testDocument()
//     * 第三个参数表示WebService方法的 返回值类型的Class对象，参数类型为Class[]。
//     *
//     * 如果被调用的WebService方法没有返回值 应使用 invokeRobust 方法 该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同。
//     *
//     * 在创建QName对象时，QName类的构造方法的第一个参数表示WSDL文件的命名空间名， 也就是 <wsdl:definitions>元素的targetNamespace属性值。
//     *
//     */
//    public static void testRPCClient() {
//        try {
//            // axis2 服务端
//            String url = "http://192.168.119.22/tds/VRVAuditPlusAlarm.asmx?wsdl";
//
//            // 使用RPC方式调用WebService
//            RPCServiceClient serviceClient = new RPCServiceClient();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            Options options = serviceClient.getOptions();
//            // 确定目标服务地址
//            options.setTo(targetEPR);
//            // 确定调用方法
//            options.setAction("http://tempuri.org/Login");
//
//            /**
//             * 指定要调用的getPrice方法及WSDL文件的命名空间 如果 WebService 服务端由axis2编写 命名空间 不一致导致的问题 org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0
//             */
//            QName qname = new QName("http://tempuri.org/", "Login");
//            // 指定getPrice方法的参数值
//            Object[] parameters = new Object[] { "admin", "123456" };
//
//            // 指定getPrice方法返回值的数据类型的Class对象
//            Class[] returnTypes = new Class[] { String.class };
//
//            // 调用方法一 传递参数，调用服务，获取服务返回结果集
//            OMElement element = serviceClient.invokeBlocking(qname, parameters);
//            // 值得注意的是，返回结果就是一段由OMElement对象封装的XML字符串。
//            // 我们可以对之灵活应用,下面我取第一个元素值，并打印之。因为调用的方法返回一个结果
//            String result = element.getFirstElement().getText();
//            System.out.println(result);
//
//            // 调用方法二 getPrice方法并输出该方法的返回值
//            Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
//            String r = (String) response[0];
//            System.out.println(r);
//
//        } catch (AxisFault e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 方法二： 应用document方式调用 用ducument方式应用现对繁琐而灵活。现在用的比较多。因为真正摆脱了我们不想要的耦合
//     */
//    public static void testDocument() {
//        try {
//            String url = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl";
//            Options options = new Options();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            options.setTo(targetEPR);
//            options.setAction("http://WebXml.com.cn/getRegionDataset");
//            ServiceClient sender = new ServiceClient();
//            sender.setOptions(options);
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            String tns = "http://WebXml.com.cn/";
//            // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的
//            OMNamespace omNs = fac.createOMNamespace(tns, "op");
//            OMElement method = fac.createOMElement("getRegionDataset", omNs);
//            method.build();
//            OMElement result = sender.sendReceive(method);
//            Iterator iterator = result.getChildren();
//            while (iterator.hasNext()) {
//                OMElement result_2 = (OMElement) iterator.next();
////                System.out.println("222222:::::::::"+result_2);
//                Iterator iterator1 = result_2.getChildElements();
//                while (iterator1.hasNext()) {
//                    OMElement result_3 = (OMElement) iterator1.next();
////                    System.out.println("333333:::::::::" + result_3);
//                    Iterator iterator3 = result_3.getChildElements();
//                    while (iterator3.hasNext()) {
//                        OMElement result_4 = (OMElement) iterator3.next();
////                        System.out.println("444444:::::::::" + result_4);
//                        Iterator iterator4 = result_4.getChildElements();
//                        while (iterator4.hasNext()) {
//                            OMElement result_5 = (OMElement) iterator4.next();
////                            System.out.println("555555:::::::::" + result_5);
//                            Iterator iterator5 = result_5.getChildElements();
//                            while (iterator5.hasNext()) {
//                                OMElement result_6 = (OMElement) iterator5.next();
//                                System.out.println("666666:::::::::" + result_6);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
//    }
//
//    public static void testTyjkService() {
//        try {
//            String url = "https://zrrk.com.cn:8443/webservice/services/TyjkService?wsdl";
//            Options options = new Options();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            options.setTo(targetEPR);
//            options.setAction("urn:process");
//            ServiceClient sender = new ServiceClient();
//            sender.setOptions(options);
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            String tns = "http://webservice.service.framework.sinobest.cn";
//            // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的
//            OMNamespace omNs = fac.createOMNamespace(tns, "");
//            OMElement method = fac.createOMElement("process", omNs);
//            String instr1 = "{\"data\":{\"USER\":\"test\",\"PASSWORD\":\"123456\",\"KIND\":\"0\",\"QLRMC\":\"邝伟明\",\"ZJH\":\"440621196511162110\",\"ZL\":\"\"},\"user\":{\"username\":\"禅城区政治生态评估系统接口\",\"password\":\"jw@2018\"},\"sqxxid\":\"930527d46469e47301647cefad033f62\"}";
//            OMElement inner1 = fac.createOMElement(instr1, omNs);
//            method.addChild(inner1);
//            String instr2 = "zrrkjson";
//            OMElement inner2 = fac.createOMElement(instr2, omNs);
//            method.addChild(inner2);
//            // 对应WebService的方法参数的节点
////            String[] strs = new String[] { "userName","password" };
//            // 参数值
////            String[] val = new String[] { "admin","123456" };
////            for (int i = 0; i < strs.length; i++) {
////                OMElement inner = fac.createOMElement(strs[i], omNs);
////                inner.setText(val[i]);
////                method.addChild(inner);
////            }
//            method.build();
//            OMElement result = sender.sendReceive(method);
//            Iterator iterator = result.getChildren();
//            while (iterator.hasNext()) {
//                OMElement result_2 = (OMElement) iterator.next();
//                System.out.println("222222:::::::::"+result_2);
//            }
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
//    }
//
//    /**
//     * 为SOAP Header构造验证信息， 如果你的服务端是没有验证的，那么你不用在Header中增加验证信息
//     *
//     * @param serviceClient
//     * @param tns
//     *            命名空间
//     * @param user
//     * @param passwrod
//     */
//    public void addValidation(ServiceClient serviceClient, String tns, String user, String passwrod) {
//        OMFactory fac = OMAbstractFactory.getOMFactory();
//        OMNamespace omNs = fac.createOMNamespace(tns, "nsl");
//        OMElement header = fac.createOMElement("AuthenticationToken", omNs);
//        OMElement ome_user = fac.createOMElement("Username", omNs);
//        OMElement ome_pass = fac.createOMElement("Password", omNs);
//
//        ome_user.setText(user);
//        ome_pass.setText(passwrod);
//
//        header.addChild(ome_user);
//        header.addChild(ome_pass);
//
//        serviceClient.addHeader(header);
//    }
//
//    public static void main(String[] args) {
//        // testRPCClient();
////        testDocument();
//        testTyjkService();
//    }
//}
