package com.yunde.util.file.writeFile;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by laisy on 2018/8/22.
 */
public class WriteFactory {

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
    }
}
