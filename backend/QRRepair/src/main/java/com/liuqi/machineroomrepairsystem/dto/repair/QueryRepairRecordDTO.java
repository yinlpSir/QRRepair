package com.liuqi.machineroomrepairsystem.dto.repair;

import lombok.Data;

@Data
public class QueryRepairRecordDTO {
    private int pageSize=8;
    private int currentPage=1;
    private String buildingName="";
}
