package com.yunde.frame.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by laisy on 2018/11/2.
 */
public class GenerCode {

    public static void main(String[] args) {
//        setFilesParams();
        setWriteContent();
    }

    public static void setWriteContent() {
        String folder = "D:\\svn\\DMForCCCD\\trunk\\data-collection-website\\src\\main\\resources\\";
        String fileType = ".xml";
        String[] files = {
                "jwXfjb", "lzdaTbmd", "qwxcbDdmfGr", "qwxcbDdmfTt",
                "qwxcbMtbd", "qwxcbYsxt", "tqwDwry", "tqwGrry", "xcbXcpj", "xfjXfxx", "zzbCdzzcy", "zzbCjgb", "zzbCmwy", "zzbDzsjqk", "zzbGbcgqk", "zzbGzryxxjbzl",
                "zzbGzryCfjl", "zzbGzryJl", "zzbGzryJljl", "zzbGzryJtgx", "zzbGzryJyjl", "zzbGzryNdkh", "zzbGzryPxjl", "zzbGzryZzmm", "zzbJxkh", "zzbSdqkhqk"
        };
        for (String fileName : files) {
            String fullPath = folder+fileName+fileType;
            StringBuilder builder = new StringBuilder();
            builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                    .append("<excels>\n")
                    .append("\t<excel id = \"").append(fileName)
                    .append("\" sheetname=\"\" class = \"org.fsdcic.lzda.entity.yb.")
                    .append(fileName).append("\" sheetIndex=\"2\">\n\n")
                    .append("\t</excel>\n")
                    .append("</excels>\n");
            writeContent(fullPath, builder.toString());
        }
    }

    private static void writeContent(String fullPath, String content) {
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

    public void useBufferOStream() {  }

    public static void setFilesParams() {
        String folder = "D:\\svn\\DMForCCCD\\trunk\\data-collection-website\\src\\main\\resources\\";
        String fileType = ".xml";
        String[] files = {
                "flXjjt", "fzbXzfy", "fzbXzss", "gafjJqtj", "gafjShaj", "gzbGlry", "jwDnwz", "jwLaqk", "jwXfjb", "lzdaTbmd", "qwxcbDdmfGr", "qwxcbDdmfTt",
                "qwxcbMtbd", "qwxcbYsxt", "tqwDwry", "tqwGrry", "xcbXcpj", "xfjXfxx", "zzbCdzzcy", "zzbCjgb", "zzbCmwy", "zzbDzsjqk", "zzbGbcgqk", "zzbGzryxxjbzl",
                "zzbGzryCfjl", "zzbGzryJl", "zzbGzryJljl", "zzbGzryJtgx", "zzbGzryJyjl", "zzbGzryNdkh", "zzbGzryPxjl", "zzbGzryZzmm", "zzbJxkh", "zzbSdqkhqk"
        };
        createFiles(folder, files, fileType);
    }

    private static void createFiles(String folder, String[] files, String fileType) {
        for (String fileName : files) {
            String fullPath = folder+fileName+fileType;
            File file = new File(fullPath);
            System.out.println(fullPath);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}