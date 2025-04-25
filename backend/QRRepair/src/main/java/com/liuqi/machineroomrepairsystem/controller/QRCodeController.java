package com.liuqi.machineroomrepairsystem.controller;

import com.google.zxing.WriterException;
import com.liuqi.machineroomrepairsystem.service.QRCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  二维码生成控制器
 */
@RestController
@RequestMapping("/qrcode")
@Validated
public class QRCodeController {
    @Autowired
    @Qualifier("qrcodeService")
    private QRCodeService qrCodeService;

    @PostMapping("/single")
    public void generateSingleQRCode(@RequestParam @NotBlank(message = "实训楼不能为空") String buildingName,
                                                       @RequestParam @NotBlank(message = "实训室不能为空") String labName,
                                                       HttpServletRequest request, HttpServletResponse response) throws IOException, WriterException {
        String protocol = request.getProtocol();// 获取请求协议
        String requestSource = request.getHeader(HttpHeaders.ORIGIN); // 获取请求的来源
        String repairPagePath = "/repair";
        ByteArrayOutputStream byteArrayOutputStream = qrCodeService.generateSingleQRCode(buildingName,labName,requestSource,repairPagePath,response);

        /**
         *
            The file at 'blob:http://172.20.10.5:8888/7f10dc6b-54f1-4d00-a04e-6d5bbf0c5c27' was loaded over an insecure connection.
            This file should be served over HTTPS.
            前端报以上错误的解决方案：
                1、以上错误可能需要你将生成的二维码先下载到本地，然后从本地获取文件传给客户端应该可以解决。这个操作相当于 加入了一个文件服务器，服务器从文件服务器获取文件传给客户端。
                2、使用 https 连接
         */
        response.setContentType("application/x-msdownload");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+
                URLEncoder.encode(buildingName+"-"+labName, StandardCharsets.UTF_8.name())+".png\"");
        try {
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            inputStream.transferTo(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
