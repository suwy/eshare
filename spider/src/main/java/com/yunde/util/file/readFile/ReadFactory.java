package com.yunde.util.file.readFile;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by laisy on 2018/8/17.
 */
public class ReadFactory {

    /**
     * 读取TXT文件内容
     * @param filePath
     * @param delimiter the delimiter that separates each element
     * @return
     */
    public String readTxt(String filePath, String delimiter) {
        StringBuilder context = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line = null;
            while((line=reader.readLine())!=null){
                context.append(line).append(delimiter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.toString();
    }

    public void readCsv(String filePath) {
        StringBuilder createSql = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line = null;
            String tableNameCn = null;
            while((line=reader.readLine())!=null){
                String item[] = line.split(",");
                String tableName = item[0];
                String colum = item[2];
                String comment = item[3];
                if (!"".equals( tableName )) {
                    if (createSql.length() != 0) {
                        createSql.deleteCharAt(createSql.length()-2).append(")COMMENT='").append(tableNameCn).append("';\n");
                    }
                    createSql.append("DROP TABLE IF EXISTS ").append(tableName)
                            .append(";\nCREATE TABLE ").append(tableName).append("(\n");
                }
                createSql.append(colum).append(" varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '")
                        .append(comment.replaceAll(" ","").replaceAll("　","")).append("',\n");
                tableNameCn = ("".equals( item[1] ) ? tableNameCn: item[1]);
            }
            createSql.deleteCharAt(createSql.length()-2).append(")COMMENT='").append(tableNameCn).append("';\n");
            System.out.println(createSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
