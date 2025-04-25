package com.liuqi.machineroomrepairsystem.dto.user;

import lombok.Data;

@Data
public class QueryUserDTO {
    private int pageSize=8;
    private int currentPage=1;
    private String username;
}
