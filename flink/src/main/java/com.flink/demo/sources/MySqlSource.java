package com.flink.demo.sources;

import com.flink.demo.entity.MyTable;
import com.flink.demo.util.MySQLKit;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author laisy
 * @date 2019/6/21
 * @description
 */
public class MySqlSource extends RichSourceFunction<MyTable> {

    PreparedStatement ps;
    private Connection connection;

    @Override
    public void run(SourceContext<MyTable> sourceContext) throws SQLException {
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            MyTable student = new MyTable(
                    resultSet.getString("name").trim(),
                    resultSet.getString("channel").trim());
            sourceContext.collect(student);
        }
    }

    @Override
    public void cancel() {
    }

    /**
     * open() 方法中建立连接，这样不用每次 invoke 的时候都要建立连接和释放连接。
     *
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = MySQLKit.getConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://locahost:3306/dbmp?useUnicode=true&characterEncoding=UTF-8",
                "",
                "");
        String sql = "select * from MyTable;";
        ps = this.connection.prepareStatement(sql);
    }

    /**
     * 程序执行完毕就可以进行，关闭连接和释放资源的动作了
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        if (connection != null) { //关闭连接和释放资源
            connection.close();
        }
        if (ps != null) {
            ps.close();
        }
    }
}