package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.pojo.User;
import com.liuqi.machineroomrepairsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void getUserByUsernameTest(){
        User user = userMapper.getUserByUsername("zs");
        System.out.println(user.getRole());
    }
    @Test
    public void getUsersTest(){
//        List<User> users = userMapper.getUsers();
//        assertNotNull(users);
//        users.forEach(System.out::println);
//        ResponseEntity<Object> response = userService.getUsers();
//        assertNotNull(response);
    }
}
