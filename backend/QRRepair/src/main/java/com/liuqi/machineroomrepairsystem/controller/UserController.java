package com.liuqi.machineroomrepairsystem.controller;

import com.liuqi.machineroomrepairsystem.annotation.CurrentUser;
import com.liuqi.machineroomrepairsystem.dto.user.LoginDTO;
import com.liuqi.machineroomrepairsystem.dto.user.QueryUserDTO;
import com.liuqi.machineroomrepairsystem.dto.user.RegisterDTO;
import com.liuqi.machineroomrepairsystem.dto.user.UpdateUserDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import com.liuqi.machineroomrepairsystem.pojo.User;
import com.liuqi.machineroomrepairsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO loginDto){
        log.info("user login !");
        return userService.login(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) throws MachineRoomRepairException {
        log.info("user register !");
        ResponseEntity<Object> result = userService.register(registerDTO);
        return result;
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<Object> getCurrentUser(@CurrentUser User user){
        HashMap<String, Object> result = new HashMap<>();
        result.put("msg","查询成功!");
        result.put("user",user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')") // @PreAuthorize("hasRole('role')")
    public ResponseEntity<Object> getUsers(QueryUserDTO userDTO){
        log.info("get users !");
        return userService.getUsers(userDTO);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> delUser(@PathVariable String username){
        log.info("del users !");
        return userService.delUser(username);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO){
        log.info("update users !");
        return userService.updateUser(updateUserDTO);
    }
}
