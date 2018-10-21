package com.yunde.frame.tools;

public class StringKit {

    /**
     * 首字母大写
     * @param phrase
     * @return
     */
    public static String uppercase(String phrase) {
        char[] cs=phrase.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
