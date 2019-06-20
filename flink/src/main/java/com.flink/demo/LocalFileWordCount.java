package com.flink.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * @author laisy
 * @date 2019/6/19
 * @description
 */
public class LocalFileWordCount {
    public static void main(String[] args) {
        LocalFileWordCount instance = new LocalFileWordCount();
        try {
//            instance.readTxt();
//            instance.readByAppend();
            instance.readExl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readExl() throws Exception {
        String path = "D:\\dmForCccd\\上传excel例子\\小文件\\禅城区处级干部信息表.xls";
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet text = env.readTextFile(path);
    }

    public void readByAppend() throws Exception{
        Path pa=new Path("D:\\");
        TextInputFormat format = new TextInputFormat(pa);
        BasicTypeInfo typeInfo = BasicTypeInfo.STRING_TYPE_INFO;
        format.setCharsetName("UTF-8");
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> st=env.readFile(format,"D:\\", FileProcessingMode.PROCESS_CONTINUOUSLY, 1L,(TypeInformation)typeInfo);
        st.print();
        env.execute();
    }

    public void readTxt() throws Exception{
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet text = env.readTextFile("D:\\idcards.txt");
        // split up the lines in pairs (2-tuples) containing: (word,1)
        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new Splitter())
                .groupBy(0).aggregate(Aggregations.SUM, 1);
        //groupBy：0表示Tuple2<String, Integer> 中的第一个元素，即分割后的单词；aggregate 1表示Tuple2<String, Integer> 中的第二个元素，即出现次数

        List<Tuple2<String,Integer>> list = counts.collect();
        for (Tuple2<String,Integer> tuple2:list){
            System.out.println(tuple2.f0 + ":" + tuple2.f1);
        }
//        counts.writeAsText("D:\\output.txt");

//        env.execute("WordCount Example");
    }

    class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            //即从文本中读取到一行字符串，按d逗号分割后得到数组tokens
            String[] tokens = value.split(",");
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }
}