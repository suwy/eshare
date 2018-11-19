package com.yunde.website.allpay.common.util;

import com.google.zxing.WriterException;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by happyyangyuan on 16/5/24.
 */
public class AllpayMgmtQrCodeHelper {
    private static final String logoPath = String.format("images%sbase%slogo.png", File.separator, File.separator);

    private static final String backgroudPath = String.format("images%sbase%sbackgroud.jpg", File.separator,
            File.separator);
    private static final String backgroudBlankPath = String.format("images%sbase%sbackgroud_blank.jpg", File.separator,
            File.separator);

    /**
     * 二维码生成
     */
    public static void createQrcode(String qrcode, String mac, String code, String type, String rootPath, OutputStream out) {
        Prop prop = PropKit.getProp("qrcode.txt");
        BufferedImage img = null;
        try {
            String logoImagePath = rootPath + logoPath;

            img = QRUtil.createQR(qrcode, 400, 400, logoImagePath);
            img = QRUtil.addWaterImage(type.equals("") ? rootPath + backgroudBlankPath : rootPath + backgroudPath, img,
                    Integer.valueOf(prop.get(type + "x")), Integer.valueOf(prop.get(type + "y")));
            img = QRUtil.addWaterText("设备编号: " + mac.substring(mac.length() - 6, mac.length()) + (!StrKit.isBlank(code) ? ("-" + String.format("%03d", Integer.parseInt(code))) : ""), img, "宋体", 0,
                    Color.BLACK, 25, Integer.valueOf(prop.get(type + "text_x")),
                    Integer.valueOf(prop.get(type + "text_y")));
            ImageIO.write(img, "jpg", out);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }
}
