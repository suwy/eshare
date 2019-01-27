package com.yunde.frame.tools;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * @author: suwy
 * @date: 2019/1/26
 * @decription:
 */
public class ZipKit {

    public static void main(String[] args) {
        try {
//            final ArrayList<File> fileAddZip = new ArrayList<File>(); // 向zip包中添加文件集合
//            fileAddZip.add(new File("/Users/ddbug/IdeaProjects/gitS/eshare/frame/src/main/java/com.yunde.frame/tools/XmlKit.java")); // 向zip包中添加一个word文件
//            zip(fileAddZip, "./testZip.zip");

            unzip("./testZip.zip","./", "123456");
        } catch (final ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void zip(ArrayList<File> files, String zipPath) throws ZipException {
        ZipFile zipFile = new ZipFile(zipPath);
        ZipParameters parameters = new ZipParameters(); // 设置zip包的一些参数集合
        parameters.setEncryptFiles(true); // 是否设置密码（此处设置为：是）
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 普通级别（参数很多）
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES); // 加密级别
        //设置aes加密强度
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("123456"); // 压缩包密码为123456
        zipFile.addFiles(files, parameters);
    }

    public static void unzip(String zipPath, String extractPath, String password) throws ZipException {
        ZipFile zipFile = new ZipFile(zipPath);
        if(zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractAll(extractPath);
    }

}
