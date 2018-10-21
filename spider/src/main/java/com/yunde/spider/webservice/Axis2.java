package com.yunde.spider.webservice;

import com.yunde.frame.tools.Const;
import com.yunde.frame.tools.ResultMsg;
import com.yunde.frame.log.YundeLog;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class Axis2 implements IWebServiceHandler {
    @Override
    public ResultMsg<OMElement> send(WebServiceEntity entity) {
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
            OMElement result = sender.sendReceive(method);
            return new ResultMsg(Const.CODE_SUCCESS, result);
        } catch (AxisFault axisFault) {
            YundeLog.error(axisFault.getMessage());
            return ResultMsg.failure(axisFault.getMessage());
        }
    }
}
