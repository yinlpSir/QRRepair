package com.liuqi.machineroomrepairsystem.service;

import com.liuqi.machineroomrepairsystem.dto.user.LoginDTO;
import com.liuqi.machineroomrepairsystem.dto.user.QueryUserDTO;
import com.liuqi.machineroomrepairsystem.dto.user.RegisterDTO;
import com.liuqi.machineroomrepairsystem.dto.user.UpdateUserDTO;
import com.liuqi.machineroomrepairsystem.exception.MachineRoomRepairException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> login(LoginDTO loginDTO);

    ResponseEntity<Object> register(RegisterDTO registerDTO) throws MachineRoomRepairException;
    ResponseEntity<Object> getUsers(QueryUserDTO userDTO);

    ResponseEntity<Object> delUser(String username);
    ResponseEntity<Object> updateUser(UpdateUserDTO updateUserDTO);
}
