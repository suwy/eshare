package com.yunde.website.allpay.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by happyyangyuan on 16/5/24.
 */
public class QrCodeHelper {
    private static final String logoPath = String.format("images%sbase%slogo.png", File.separator, File.separator);

    private static final String backgroudBlankPath = String.format("images%sbase%sbackgroud_blank.jpg", File.separator,
            File.separator);
    /**
     * 二维码生成
     */
    public static void createQrcode(String qrcode, String waterText, String rootPath, OutputStream out, boolean needLogo) {
        Prop prop = PropKit.getProp("qrcode.txt");
        BufferedImage img;
        try {
            String logoImagePath = needLogo ? rootPath+logoPath : null;

            img = QRUtil.createQR(qrcode, 400, 400, logoImagePath);
            if(needLogo){
                img = QRUtil.addWaterImage(rootPath+backgroudBlankPath, img,
                        Integer.valueOf(prop.get("x")), Integer.valueOf(prop.get("y")));
            }
            if(StrKit.notBlank(waterText)){
                img = QRUtil.addWaterText(waterText, img, "宋体", 0,
                        Color.BLACK, 25, Integer.valueOf(prop.get("text_x")),
                        Integer.valueOf(prop.get("text_y")));
            }
            ImageIO.write(img, "jpg", out);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

//    public static void createQrcode(ModuleDeviceBean device, Controller controller){
//        String root = controller.getRequest().getSession().getServletContext().getRealPath("/") + File.separator;
//
//        ServletOutputStream out = null;
//        try {
//            String content = PropKit.get("allpay_qrcode_url").replace("#qrcode", device.getDeviceQrCode16());
//            out = controller.getResponse().getOutputStream();
//            controller.getResponse().reset();
//            controller.getResponse().setHeader("content-disposition", String.format("attachment;filename=%s.jpg",
//                    URLEncoder.encode(device.getDeviceMac(), "UTF-8")));
//            String mac = device.getDeviceMac(), code = device.getHomemadeId();
//            String waterText = "设备编号: " + mac.substring(mac.length() - 6, mac.length())+(!StrKit.isBlank(code)?("-"+String.format("%03d", Integer.parseInt(code))):"");
//            QrCodeHelper.createQrcode(content, waterText, root, out, true);
//            out.flush();
//            if (!StrKit.valueEquals("1", device.get("isDownload"))) {
//                device.setIsDownload(1);
//                DeviceService.me.updateDevice(device);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        controller.renderNull();
//    }

    public static void createScanActQrcode(Integer activityId, Controller controller) throws WriterException {
        String root = controller.getRequest().getSession().getServletContext().getRealPath("/") + File.separator;

        ServletOutputStream out = null;
        try {
            String content = PropKit.get("allpay_activity_url") + "/marketing/scanGive/" + HashKit.encryptToAes(activityId.toString(), "go4allpay");
            out = controller.getResponse().getOutputStream();
            controller.getResponse().reset();
            controller.getResponse().setHeader("content-disposition", String.format("attachment;filename=%s.jpg",
                    URLEncoder.encode(activityId.toString(), "UTF-8")));

            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 0);
            
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, 400, 400, hints);// 生成矩阵
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        controller.renderNull();
    }
}
