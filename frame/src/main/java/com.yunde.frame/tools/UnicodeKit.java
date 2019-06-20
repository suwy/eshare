package com.yunde.frame.tools;

/**
 * @author laisy
 * @date 2019/4/19
 * @description unicode编码相互转换
 */
public class UnicodeKit {

    public static String encode(String src) {
        if (StringKit.isNullOrEmpty(src)) {
            return null;
        }
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < src.length(); i++) {
            c = src.charAt( i );
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                str.append("\\u").append(strHex);
            } else {
                /**
                 * 低位在前面补00
                 */
                str.append( "\\u00").append(strHex);
            }
        }
        return str.toString();
    }


    public static String decode(String src) {
        int t =  src.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            /**
             * 每6位描述一个字节
             */
            String s = src.substring(i * 6, (i + 1) * 6);
            /**
             * 高位需要补上00再转
             */
            String s1 = s.substring(2, 4) + "00";
            /**
             * 低位直接转
             */
            String s2 = s.substring(4);
            /**
             * 将16进制的string转为int
             */
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            /**
             * 将int转换为字符
             */
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

}