package com.yunde.spider;

import com.yunde.frame.tools.IEnum;

/**
 * Created by laisy on 2018/10/18.
 */
public enum RequestTypeEnum implements IEnum {
    CXF,
    AXIS2;

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public static void main(String[] args) {
        System.out.println(RequestTypeEnum.AXIS2.getCode());
    }
}