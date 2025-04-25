package com.liuqi.machineroomrepairsystem.utils;

import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeDeWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.mysql.cj.util.Base64Decoder;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;

/**
 *  二维码工具类
 *
 *  QrCodeGenWrapper 二维码生成包装类
 *  QrCodeDeWrapper 二维码解析包装类
 */
public class QRCodeUtil {
    /**
     * 二维码生成最基本的示例：
        // 生成一个最常见的最普通的二维码，并保存为指定目录下
         String msg = "http://172.20.10.5:8888/login";
         try {
             boolean ans = QrCodeGenWrapper.of(msg).asFile("src/test/qrcode/qrcode.png");
            System.out.println(ans);
         } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
         }
     */

    /**
     *  二维码生成
     */
    public static BufferedImage generateQRCode(String msg) { //String agreement,String host,String port,String path
        int qrcodeWidth = 250;
        int qrcodeHeight = 250;
        // 读取 logo
        InputStream inputStream = QRCodeUtil.class.getResourceAsStream("/static/images/logo.png");
        BufferedImage qrcode = null;
        try {
            qrcode = QrCodeGenWrapper.of(msg)
                    .setW(qrcodeWidth)
                    .setH(qrcodeHeight)
                    .setLogo(inputStream) // 重载方法：setLogo(String logoPath)
                    .setLogoStyle(QrCodeOptions.LogoStyle.CIRCLE) // 设置圆形logo
                    .setLogoBgColor(0xfffefefe) // logo bgColor
                    .setLogoBorderBgColor(0xffc7c7c7) // border color
                    .setLogoBorder(true) // logo border
                    .setDetectSpecial() // 对 二维码探测图形 做特殊处理
                    .setDetectOutColor(Color.BLACK) // 定位点(探测图形)外边颜色
                    .setDetectInColor(Color.GREEN) // 定位点内部颜色
                    .asBufferedImage();
        } catch (Exception e){
            System.out.println("create qrcode error! e: " + e);
        }
        return qrcode;
    }

    /**
     * 二维码解析
     * 二维码解析支持针对本地/网络中的二维码进行解码
     */
    public static void deparseQrCode() throws NotFoundException, IOException {
        String img = "https://static.oschina.net/uploads/img/201707/09205944_0PzS.jpg";

        String ans = QrCodeDeWrapper.decode(img);
        System.out.println(ans);
    }

    /**
     *  加工二维码
     */
    public static ByteArrayOutputStream processQRCode(String buildingName, String labName, String msg){
        BufferedImage qrcode = generateQRCode(msg);

        BufferedImage picture = new BufferedImage(600, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) picture.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 300);
        g.drawImage(qrcode, 300, 20, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 20));
        g.drawString("所在实训楼:" + buildingName, 10, 75);
        g.drawString("所在实训室:" + labName, 10, 125);
        g.drawString("若当前电脑出现问题", 10, 175);
        g.drawString("请扫描右侧二维码进行报修", 10, 225);
        g.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(picture,"png",byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }
}
