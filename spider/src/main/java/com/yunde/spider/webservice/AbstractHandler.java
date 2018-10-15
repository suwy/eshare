package com.yunde.spider.webservice;

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
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by laisy on 2018/8/14.
 */
public abstract class AbstractHandler {

    private final WSEntity entity;
    private List list = new ArrayList();

    public AbstractHandler(WSEntity entity) {
        this.entity = entity;
    }

    public OMElement send() {
        try {
            Options options = new Options();
            EndpointReference targetEPR = new EndpointReference(entity.getUrl());
            options.setTo(targetEPR);
            options.setAction(entity.getAction());
            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            OMNamespace omNs = fac.createOMNamespace(entity.getNamespace(), "");
            OMElement method = fac.createOMElement(entity.getMethod(), omNs);
            System.out.println(entity.toString());
            return sender.sendReceive(method);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
            return null;
        }
    }

    public OMElement sendByAxis2() {
        try {
            Options options = new Options();
            EndpointReference targetEPR = new EndpointReference(entity.getUrl());
            options.setTo(targetEPR);
            options.setAction(entity.getAction());
            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            OMNamespace omNs = fac.createOMNamespace(entity.getNamespace(), "");
            OMElement method = fac.createOMElement(entity.getMethod(), omNs);
            System.out.println(entity.toString());
            return sender.sendReceive(method);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
            return null;
        }
    }

    public String sendByCxf() {
        try {
            System.out.println(entity.toString());
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            org.apache.cxf.endpoint.Client client = dcf.createClient(entity.getUrl());
            Object[] objects = client.invoke(entity.getMethod(), entity.getParams(), entity.getOthers());
            return objects[0].toString();
        } catch (URISyntaxException u) {
            u.printStackTrace();
        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void parse(OMElement result, String nodeName) {
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
                    this.list.add(map);
                }
            }
        }
    }

    protected List getList() {
        return list;
    }
}
