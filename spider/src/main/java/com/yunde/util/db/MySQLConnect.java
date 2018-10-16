package com.yunde.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by laisy on 2018/8/22.
 */
public class MySQLConnect {

    public static Connection getConnect() {
        Connection con = null;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://19.134.193.65:13306/qjwdb??useUnicode=true&characterEncoding=utf8&useSSL=false&rewriteBatchedStatements=true";
        //MySQL配置时的用户名
        String user = "uapp";
        //MySQL配置时的密码
        String password = "Fsxxb123=";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            return con;
        } catch(Exception e) {
            try {
                if(null == con) con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } finally {
            try {
                if(null == con) con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return con;
    }

    public static void insertDatas(String sql) {
        Connection connection = getConnect();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            boolean result = statement.execute(sql);
            System.out.println(String.format("执行语句 %s结果为%s", sql, result));
//            new WriteTxt().write2File("D:\\sgtj\\logs\\log.txt", String.format("执行语句 %s结果为%s", sql, result));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
