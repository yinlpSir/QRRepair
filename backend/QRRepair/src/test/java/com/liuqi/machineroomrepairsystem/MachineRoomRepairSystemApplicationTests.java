package com.liuqi.machineroomrepairsystem;

import com.liuqi.machineroomrepairsystem.mapper.UserMapper;
import com.liuqi.machineroomrepairsystem.pojo.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

@SpringBootTest
class MachineRoomRepairSystemApplicationTests {

    @BeforeEach
    public void init(){
        System.out.println("init...");
    }
    @AfterEach
    public void destroy(){
        System.out.println("destroy...");
    }

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Test
    public void getUserByUsernameTest(){
        User user = userMapper.getUserByUsername("zs");
        System.out.println(user.getRole());
    }

    @Test
    void contextLoads() {
        System.out.println("class");
    }

}
