package com.yunde.spider.webservice;

import com.yunde.frame.log.YundeLog;
import com.yunde.spider.BaseHandler;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Created by laisy on 2018/8/13.
 */
public class WebServiceHandler implements BaseHandler {

    private final WebServiceEntity entity;

    public WebServiceHandler(WebServiceEntity entity) {
        this.entity = entity;
    }

    public List getNodeList(OMElement element, String parentNode) {
        return parse(element, parentNode);
    }

    //通过Axis2请求
    public OMElement sendByAxis2() {
        YundeLog.info("请求参数" +entity.toString());
        try {
            Options options = new Options();
            EndpointReference targetEPR = new EndpointReference( entity.getUrl());
            options.setTo(targetEPR);
            options.setAction(entity.getAction());
            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            OMNamespace omNs = fac.createOMNamespace(entity.getNamespace(), "");
            OMElement method = fac.createOMElement(entity.getMethod(), omNs);
            return sender.sendReceive(method);
        } catch (AxisFault axisFault) {
            YundeLog.error(axisFault.getMessage());
        } finally {
            return null;
        }
    }

    //通过Cxf请求
    public String sendByCxf() {
        YundeLog.info("请求参数" +entity.toString());
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            org.apache.cxf.endpoint.Client client = dcf.createClient(entity.getUrl());
            Object[] objects = client.invoke(entity.getMethod(), entity.getParams(), entity.getOthers());
            return objects[0].toString();
        } catch (Exception e) {
            YundeLog.error(e.getMessage());
        } finally {
            return null;
        }
    }

    //XML格式处理
    private List parse(OMElement result, String nodeName) {
        List list = new ArrayList();
        Iterator iterator = result.getChildElements();
        QName qName = new QName(nodeName);
        while (iterator.hasNext()) {
            OMElement element = (OMElement) iterator.next();
            if (!element.getQName().equals(qName)) {
                parse(element, nodeName);
            } else {
                Map map = new HashMap();
                Iterator<OMElement> iter = element.getChildElements();
                while (iter.hasNext()) {
                    OMElement node = iter.next();
                    map.put(node.getLocalName(), node.getText());
                    list.add(map);
                }
            }
        }
        return list;
    }
}
