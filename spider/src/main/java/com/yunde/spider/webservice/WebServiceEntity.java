package com.yunde.spider.webservice;

/**
 * Created by laisy on 2018/8/13.
 * WebService 请求参数模型
 */
public class WebServiceEntity {

    private String url;
    private String action;
    private String namespace;
    private String method;
    private String params;
    private String others;

    public WebServiceEntity(String url, String action, String namespace, String method, String params) {
        this.url = url;
        this.action = action;
        this.namespace = namespace;
        this.method = method;
        this.params = params;
    }

    public WebServiceEntity(String url, String method, String params, String others) {
        this.url = url;
        this.method = method;
        this.params = params;
        this.others = others;
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

    public String toString() {
        return String.format(" url:%s\n action:%s\n namespace:%s\n method:%s\n params:%s\n others:%s",
                this.url, this.action, this.namespace, this.method, this.params, this.others);
    }
}
