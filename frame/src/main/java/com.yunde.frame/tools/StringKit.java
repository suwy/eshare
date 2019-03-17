package com.yunde.frame.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author suwy
 * @date 2019/2/21
 * @description 字符串工具
 */
public class StringKit {

    public static final char UNDERLINE='_';
    private static final char _a='a';
    private static final char _z='z';

    /**
     * 判断是否为null或者空字符串
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(Object value) {
        return value == null || value.toString().trim().equals("");
    }

    /**
     * 转驼峰式
     * @param value
     * @return
     */
    public static String toCamelCase(String value, boolean isUpperFirst){
        /**
         * 1. 把全部转成小写
         * 2. 把下划线全部去掉
         * 3. 把下划线之后的一个字母变成大写
         * 4. 首个字母变成大写
         */
        if (isNullOrEmpty(value)){
            return "";
        }
        StringBuilder key = new StringBuilder(value.toLowerCase());
        Matcher mc= Pattern.compile(String.valueOf(UNDERLINE)).matcher(value);
        int i = 0;
        while (mc.find()){
            int position=mc.end()-(i++);
            key.replace(position-1,position+1,key.substring(position,position+1).toUpperCase());
        }
        if (isUpperFirst) {
            return toUpperFirst(key.toString());
        }
        return key.toString();
    }

    /**
     * 首字母大写
     * @param value
     * @return
     */
    public static String toUpperFirst(String value) {
        char[] chars = value.toCharArray();
        if (chars[0] >= _a && chars[0] <= _z) {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
    }

    /**
     * 类名形式
     * @param value
     * @return
     */
    public static String toClassCase(String value) {
        return toCamelCase(value, true);
    }
}
