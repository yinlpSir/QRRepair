package com.liuqi.machineroomrepairsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lab {
    private String id;
    private String labName;
    private String labAlias;
    private int availableEquipment; // 可用设备数量
    private int damageEquipment; // 损坏设备数量
    private String buildingId;
    private TrainingBuilding trainingBuilding;
}
