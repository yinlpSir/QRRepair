package com.liuqi.machineroomrepairsystem.service;

import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface QRCodeService {
    /**
     * 生成单张二维码
     *
     * @param buildingName   要报修的 实训楼
     * @param labName        要报修的 实训室
     * @param requestSource  请求的来源.它应包含 协议+主机+端口.例如：http://192.168.114.1:8888
     * @param repairPagePath 报修页面路径.该参数会与requestSource拼接成一个完整的url，以确保扫描二维码访问到报修页面
     * @return
     */
    public ByteArrayOutputStream generateSingleQRCode(String buildingName, String labName,
                                                      String requestSource, String repairPagePath, HttpServletResponse response) throws IOException, WriterException;
}
