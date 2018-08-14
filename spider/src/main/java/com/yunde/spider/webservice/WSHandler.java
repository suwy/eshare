package com.yunde.spider.webservice;

import java.util.List;

/**
 * Created by laisy on 2018/8/13.
 */
public class WSHandler extends AbstractHandler {

    public WSHandler(WSEntity entity) {
        super(entity);
    }

    public List getNodeList(String parentNode) {
        parse(send(), parentNode);
        return super.getList();
    }
}
