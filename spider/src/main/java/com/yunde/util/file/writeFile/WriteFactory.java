package com.yunde.util.file.writeFile;

import java.io.*;

/**
 * Created by laisy on 2018/8/22.
 */
public class WriteFactory {

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
        BufferedOutputStream Buff = null;
        int count = 1000;//写文件行数
        long begin0 = System.currentTimeMillis();
        try {
            File file = new File("D:/lzda/json/"+"gbxx.json");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
//            file.createNewFile();
            outSTr = new FileOutputStream(file);
            Buff = new BufferedOutputStream(outSTr);
            for (int i = 0; i < count; i++) {
                Buff.write("测试java 文件操作\r\n".getBytes());
            }
            Buff.flush();
            Buff.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Buff.close();
                outSTr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end0 = System.currentTimeMillis();
    }


}
