package com.yunde.frame.tools;

import java.io.*;

public class FileKit {

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

    //要换方法，耗的时间太久了
    @Deprecated
    public static void write2File(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write((content+"\r\n").getBytes());
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream outSTr = null;
        BufferedOutputStream buff = null;
        int count = 1000;//写文件行数
        long begin0 = System.currentTimeMillis();
        try {
            File file = new File("D:/lzda/json/"+"gbxx.json");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            outSTr = new FileOutputStream(file);
            buff = new BufferedOutputStream(outSTr);
            for (int i = 0; i < count; i++) {
                buff.write("测试java 文件操作\r\n".getBytes());
            }
            buff.flush();
            buff.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buff.close();
                outSTr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end0 = System.currentTimeMillis();
    }

    public static void writeContent(String fullPath, String content) {
        BufferedOutputStream bos = null;
        try {
            File file = new File(fullPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file,true);
            bos = new BufferedOutputStream(fos);
            long begin = System.currentTimeMillis();
            bos.write(content.getBytes());
            System.out.println("BufferedOutputStream执行耗时: " + (System.currentTimeMillis() - begin));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
