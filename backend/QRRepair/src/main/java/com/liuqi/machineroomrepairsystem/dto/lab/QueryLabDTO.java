package com.liuqi.machineroomrepairsystem.dto.lab;

import lombok.Data;

@Data
public class QueryLabDTO {
    private int pageSize=8;
    private int currentPage=1;
    private String buildingName;
    private String labName;
}
