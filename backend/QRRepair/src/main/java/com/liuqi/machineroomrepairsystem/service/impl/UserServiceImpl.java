package com.liuqi.machineroomrepairsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuqi.machineroomrepairsystem.dto.user.LoginDTO;
import com.liuqi.machineroomrepairsystem.dto.user.QueryUserDTO;
import com.liuqi.machineroomrepairsystem.dto.user.RegisterDTO;
import com.liuqi.machineroomrepairsystem.dto.security.CustomUserDetails;
import com.liuqi.machineroomrepairsystem.dto.user.UpdateUserDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import com.liuqi.machineroomrepairsystem.mapper.UserMapper;
import com.liuqi.machineroomrepairsystem.pojo.User;
import com.liuqi.machineroomrepairsystem.service.UserService;
import com.liuqi.machineroomrepairsystem.utils.JwtUtil;
import com.liuqi.machineroomrepairsystem.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Object> login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(Objects.isNull(authentication)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResult("用户名或密码错误!",null));
        }
        var result = new HashMap<String,Object>();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        result.put("username", userDetails.getUsername());
        byte role = userDetails.getRole();
        result.put("authority",role == 2?"user":"boss");
        result.put("token", JwtUtil.generateToken(userDetails.getUsername()));// 签发token
        return ResponseEntity.ok(buildResult("登录成功!",result));
    }

    @Override
    public ResponseEntity<Object> register(RegisterDTO registerDTO) throws MachineRoomRepairException {
        if(Objects.nonNull(userMapper.getUserByUsername(registerDTO.getUsername()))){
            throw new MachineRoomRepairException(HttpStatus.BAD_REQUEST,registerDTO.getUsername()+" 已存在!");
        }
        User user = new User();
        BeanUtils.copyProperties(registerDTO,user);
        String userID = UUID.randomUUID().toString().replace("-","");
        user.setId(userID);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int effectedRow = userMapper.insertUser(user);
        if(effectedRow > 0) return ResponseEntity.ok(buildResult("注册成功！",null));
        throw new MachineRoomRepairException("注册失败!");
    }

    @Override
    public ResponseEntity<Object> getUsers(QueryUserDTO userDTO) {
        log.info(String.format("分页查询用户条件集：%s",userDTO.toString()));

        PageHelper.startPage(userDTO.getCurrentPage(),userDTO.getPageSize());
        if(Objects.isNull(userDTO.getUsername())) userDTO.setUsername("");
        List<User> users = userMapper.getUsers(userDTO.getUsername());
        PageInfo<User> userPageInfo = new PageInfo<>(users);

        // 封装分页查询结果集
        PagingVO<User> result = new PagingVO<>();
        result.setData(userPageInfo.getList());
        result.setTotal(userPageInfo.getTotal());
        result.setCurrentPage(userPageInfo.getPageNum());
        result.setPageSize(userPageInfo.getPageSize());
        result.setTotalPages(userPageInfo.getPages());
        return ResponseEntity.ok(buildResult("查询成功!",result));
    }

    @Override
    public ResponseEntity<Object> delUser(String username) {
        log.info(String.format("删除%s用户",username));

        int effectedRow = userMapper.delUserByUsername(username);
        return ResponseEntity.ok(buildResult("删除成功！",null));
    }

    @Override
    public ResponseEntity<Object> updateUser(UpdateUserDTO updateUserDTO) {
        log.info(String.format("更新用户条件集：%s",updateUserDTO.toString()));
        int effectedRow = userMapper.updateUserById(updateUserDTO);
        return ResponseEntity.ok(buildResult("修改成功!",null));
    }

    private HashMap<String,Object> buildResult(String message, @Nullable Object data){
        var result = new HashMap<String,Object>();
        result.put("msg",message);
        result.put("data",data);
        return result;
    }
}
