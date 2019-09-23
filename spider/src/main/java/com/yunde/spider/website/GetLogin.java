package com.yunde.spider.website;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: suwy
 * @date: 2019/2/1
 * @decription:
 */
public class GetLogin {

    public static void main(String[] args) throws IOException {
        // 连接地址（通过阅读html源代码获得，即为登陆表单提交的URL）
        String surl = "https://login.51job.com/ajax/login.php";

        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
         * java.net.URL and //java.net.URLConnection
         */
        URL url = new URL(surl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        /**
         * 最后，为了得到OutputStream，简单起见，把它约束在并且放入POST信息中，例如： ...
         */
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "GBK");
        //其中的memberName和password也是阅读html代码得知的，即为表单中对应的参数名称
        out.write("loginname=icesuke@126.com&password=1qazXSW@&action=save&from_domain=i&lang=c&isread=on"); // post的关键所在！
        // remember to clean up
        out.flush();
        out.close();

        // 取得cookie，相当于记录了身份，供下次访问时使用
        String cookieVal = connection.getHeaderField("Set-Cookie");
        System.out.println(cookieVal);

        int code = connection.getResponseCode();
        System.out.println(code);

        BufferedReader reader = new BufferedReader
                (new InputStreamReader(connection.getInputStream(),"GBK"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();

        /**
         * https://login.51job.com/ajax/verifycode.php?type=33&from_domain=my&t=311457763
         * verifycode.php
         * https://login.51job.com/ajax/verifycode.php?from_domain=my&t=1549369909175
         *
         *
         * https://img01.51jobcdn.com/im/pm/jinying/banner2019.1.30/51web_banner_315x70.png?1548836977
         */

        /**
         * https://search.51job.com/list/030600,000000,0000,00,9,99,Java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=
         *
         * https://search.51job.com/list/030600,000000,0000,00,9,99,%25E9%2594%2580%25E5%2594%25AE,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=
         *
         * 030600 佛山
         * 030200 广州
         *
         *
         */

    }
}
