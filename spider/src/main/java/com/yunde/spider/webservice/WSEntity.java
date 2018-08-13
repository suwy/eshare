package com.yunde.spider.webservice;

/**
 * Created by laisy on 2018/8/13.
 */
public class WSEntity {

    private String url;
    private String action;
    private String namespace;
    private String method;

    public String getUrl() {
        return url;
    }

    public WSEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getAction() {
        return action;
    }

    public WSEntity setAction(String action) {
        this.action = action;
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public WSEntity setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public WSEntity setMethod(String method) {
        this.method = method;
        return this;
    }
}
