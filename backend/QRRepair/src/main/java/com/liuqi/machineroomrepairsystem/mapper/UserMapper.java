package com.liuqi.machineroomrepairsystem.mapper;

import com.liuqi.machineroomrepairsystem.dto.user.UpdateUserDTO;
import com.liuqi.machineroomrepairsystem.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    @Select("select * from user where phone_number = #{phoneNumber}")
    User getUserByPhoneNumber(String phoneNumber);

    @Insert("insert into user(id,username,password,phone_number) values (#{user.id},#{user.username},#{user.password},#{user.phoneNumber})")
    int insertUser(@Param("user") User user);

    @Select("select id,username,phone_number,role from user where username like CONCAT('%',#{username},'%')")
    List<User> getUsers(@Param("username") String username);

    @Delete("delete from user where username = #{username}")
    int delUserByUsername(String username);

    @Update("update user set username = #{params.username},phone_number=#{params.phoneNumber},role=#{params.role} where id=#{params.id}")
    int updateUserById(@Param("params") UpdateUserDTO updateUserDTO);
}
