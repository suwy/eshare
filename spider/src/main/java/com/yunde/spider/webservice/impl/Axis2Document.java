package com.yunde.spider.webservice.impl;

import com.yunde.spider.webservice.WSEntity;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Created by laisy on 2018/8/13.
 */
public class Axis2Document {

    private WSEntity entity;
    private List list = new ArrayList();

    public Axis2Document(WSEntity entity) {
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
            return sender.sendReceive(method);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
            return null;
        }
    }

    public void parse(OMElement result, String nodeName) {
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

    public List getNodeList(String parentNode) {
        parse(send(), parentNode);
        return this.list;
    }
}
