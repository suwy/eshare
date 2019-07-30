package com.yunde.frame.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * 收集异常堆栈信息
     */
    public static String collectErrorStackMsg(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String strs = sw.toString();
        return strs;
    }

    /*
     * 身份证验证
     **/
    public static boolean isCardId(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" + "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X //^开头 //[1-9] 第一位1-9中的一个      4 //\\d{5} 五位数字  10001（前六位省市县地区） //(18|19|20)  19（现阶段可能取值范围18xx-20xx年） //\\d{2} 91（年份） //((0[1-9])|(10|11|12))     01（月份） //(([0-2][1-9])|10|20|30|31)01（日期） //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女） //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值） //$结尾 //假设15位身份证号码:410001910101123  410001 910101 123 //^开头 //[1-9] 第一位1-9中的一个      4 //\\d{5} 五位数字           10001（前六位省市县地区） //\\d{2}                    91（年份） //((0[1-9])|(10|11|12))     01（月份） //(([0-2][1-9])|10|20|30|31)01（日期） //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X //$结尾
        boolean matches = IDNumber.matches(regularExpression); //判断第18位校验值
        if (matches) {
            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray(); //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i]; sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println(IDNumber+"身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }
        }
        return matches;
    }

    private static List<String> split(final String str, final String separatorChars, final int max, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list;
    }

    public static List<String> split(final String str, final String separatorChars) {
        return split(str, separatorChars, -1, false);
    }
}
