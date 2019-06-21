package com.flink.demo;

import com.flink.demo.sources.MySqlSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author laisy
 * @date 2019/6/20
 * @description
 */
public class ConnectMysql {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(new MySqlSource()).print();
        env.execute("start ...");
    }
}