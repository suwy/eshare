package com.yunde.spider.website;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author: suwy
 * @date: 2019/2/1
 * @decription:
 */
public class Get51job {

    /**
     * https://search.51job.com/list/030200,000000,0100,01,9,99,%2520,2,1.html
     * 030200 ： 地点
     * 000000 ：
     * 0100 ：行业
     * 01 ：职能
     *
     * https://search.51job.com/list/030200,000000,0100,01,0,99,Android,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=5&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=
     *
     * https://search.51job.com/list/030200,000000,0100,01,9,99,Android,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=5&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=
     */
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://search.51job.com/list/030200,000000,0100,01,9,99,Android,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=");
        BufferedReader reader = new BufferedReader
                (new InputStreamReader(url.openStream(),"GBK"));
        BufferedWriter writer = new BufferedWriter
                (new FileWriter("51job.html"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            writer.write(line);
            writer.newLine();
        }
        reader.close();
        writer.close();
    }
}
