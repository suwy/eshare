package com.yunde.spider.webservice;

import com.yunde.spider.IBaseHandler;

/**
 * Created by laisy on 2018/10/18.
 */
public interface IWebServiceHandler extends IBaseHandler<WebServiceEntity> {

    Object send(WebServiceEntity entity);
}
