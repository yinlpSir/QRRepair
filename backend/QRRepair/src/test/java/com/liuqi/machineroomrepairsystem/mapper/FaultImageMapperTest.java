package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.FaultImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FaultImageMapperTest {
    @Autowired
    private FaultImageMapper faultImageMapper;
    @Test
    public void test() throws IOException {
        FaultImage faultImage = faultImageMapper.getImageById("53d905c293f146ee8da1cf0496d25b84");
        InputStream inputStream = faultImage.getImage();
        inputStream.transferTo(new FileOutputStream(new File("d:\\test-data",faultImage.getId()+".png")));
        assertNotNull(faultImage);
    }
}
