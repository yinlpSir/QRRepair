package com.liuqi.machineroomrepairsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.liuqi.machineroomrepairsystem.mapper")
@EnableTransactionManagement // 开启事务注解支持
public class MachineRoomRepairSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MachineRoomRepairSystemApplication.class, args);
    }

}
