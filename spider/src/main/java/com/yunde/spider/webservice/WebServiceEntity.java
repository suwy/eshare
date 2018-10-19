package com.yunde.spider.webservice;

import com.yunde.spider.RequestTypeEnum;

import java.io.Serializable;

/**
 * Created by laisy on 2018/8/13.
 * WebService 请求参数模型
 */
public class WebServiceEntity implements Serializable {

    /**
     * 请求地址
     */
    private String url;
    /**
     * 动作
     */
    private String action;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 方法名
     */
    private String method;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 其他参数
     */
    private String others;

    /**
     * 请求类型
     */
    private RequestTypeEnum requestTypeEnum;

    public WebServiceEntity(String url, String action, String namespace, String method, String params, RequestTypeEnum requestTypeEnum) {
        this.url = url;
        this.action = action;
        this.namespace = namespace;
        this.method = method;
        this.params = params;
        this.requestTypeEnum = requestTypeEnum;
    }

    public WebServiceEntity(String url, String method, String params, String others, RequestTypeEnum requestTypeEnum) {
        this.url = url;
        this.method = method;
        this.params = params;
        this.others = others;
        this.requestTypeEnum = requestTypeEnum;
    }

    public String getUrl() {
        return url;
    }

    public String getAction() {
        return action;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getMethod() {
        return method;
    }

    public String getParams() {
        return params;
    }

    public String getOthers() {
        return others;
    }

    public RequestTypeEnum getRequestTypeEnum() {
        return requestTypeEnum;
    }

    @Override
    public String toString() {
        return String.format(" url:%s\n action:%s\n namespace:%s\n method:%s\n params:%s\n others:%s\n requestTypeEnum:%s",
                this.url, this.action, this.namespace, this.method, this.params, this.others, this.requestTypeEnum);
    }
}
