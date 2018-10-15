package com.yunde.spider.webservice;

import org.apache.axiom.om.OMElement;

import java.util.List;

/**
 * Created by laisy on 2018/8/13.
 */
public class WSHandler extends AbstractHandler {

    public WSHandler(WSEntity entity) {
        super(entity);
    }

    public List getNodeList(OMElement element, String parentNode) {
        parse(element, parentNode);
        return super.getList();
    }

//    public OMElement send() {
//        super;
//    }
}
