package com.yunde.spider.webservice;

/**
 * Created by laisy on 2018/8/13.
 * WebService 参数模型
 */
public class WSEntity {

    private String url;
    private String action;
    private String namespace;
    private String method;

    public WSEntity(String url, String action, String namespace, String method) {
        this.url = url;
        this.action = action;
        this.namespace = namespace;
        this.method = method;
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

    public String toString() {
        return String.format(" url:%s\n action:%s\n namespace:%s\n method:%s\n",this.url, this.action, this.namespace, this.method);
    }
}
