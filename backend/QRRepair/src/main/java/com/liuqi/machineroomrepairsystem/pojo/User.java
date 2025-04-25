package com.liuqi.machineroomrepairsystem.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String username;
//    @JsonIgnore
    private String password;
    private String phoneNumber;
    private byte role; // 1 is admin ; 2 is common ;
}

