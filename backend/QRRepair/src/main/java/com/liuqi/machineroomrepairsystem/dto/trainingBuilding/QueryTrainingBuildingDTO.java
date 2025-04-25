package com.liuqi.machineroomrepairsystem.dto.trainingBuilding;

import lombok.Data;

@Data
public class QueryTrainingBuildingDTO {
    private int pageSize=8;
    private int currentPage=1;
    private String buildingName;
}
