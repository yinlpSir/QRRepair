package com.liuqi.machineroomrepairsystem.dto.lab;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAndUpdateLabDTO {

    private String id;

    @NotBlank(message = "实训楼不能为空")
    private String buildingName;

    @NotBlank(message = "实训室不能为空")
    private String labName;

    private String labAlias;

    @Range(min = 0,max = 1000)
    private int availableEquipment=0; // 可用设备数量

    @Range(min = 0,max = 1000)
    private int damageEquipment=0; // 损坏设备数量
}
