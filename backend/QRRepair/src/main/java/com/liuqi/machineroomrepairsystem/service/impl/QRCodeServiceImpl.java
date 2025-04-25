package com.liuqi.machineroomrepairsystem.service.impl;

import com.liuqi.machineroomrepairsystem.service.QRCodeService;
import com.liuqi.machineroomrepairsystem.utils.QRCodeUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Slf4j
@Service("qrcodeService")
public class QRCodeServiceImpl implements QRCodeService {

    @Override
    public ByteArrayOutputStream generateSingleQRCode(String buildingName, String labName,
                                                      String requestSource, String repairPagePath, HttpServletResponse response) {
        // 访问前端页面的url
        String url = requestSource.concat(repairPagePath).concat("?buildingName="+buildingName).concat("&labName="+labName);
        log.info(String.format("二维码的url:%s",url));
        return QRCodeUtil.processQRCode(buildingName,labName,url);
    }
}
