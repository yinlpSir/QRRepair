package com.liuqi.machineroomrepairsystem.other;

import com.google.zxing.NotFoundException;
import com.liuqi.machineroomrepairsystem.utils.QRCodeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class QRCodeTest {
    @Test
    public void qrCodeTest(){
        QRCodeUtil.processQRCode("创新楼","1106","http://172.20.10.5:8888/repair");
//        QRCodeUtil.generateQRCode();
//        try {
//            QRCodeUtil.generateQRCode();
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
