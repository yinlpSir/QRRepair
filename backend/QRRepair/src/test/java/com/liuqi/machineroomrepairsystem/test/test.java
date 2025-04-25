package com.liuqi.machineroomrepairsystem.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {
    @Test
    public void test(){
        Person student = new Student();
        student.handle();
    }
}
