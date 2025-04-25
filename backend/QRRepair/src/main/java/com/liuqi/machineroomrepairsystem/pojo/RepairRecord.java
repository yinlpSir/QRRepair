package com.liuqi.machineroomrepairsystem.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 报修记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairRecord {
    private String id;
    private int equipmentNo; // 报修设备编号
    private String description;// 故障描述
    private byte status=1; // 报修状态 （1是待处理,2是维修中,3是已完成）
    private LocalDateTime repairTime = LocalDateTime.now().withNano(0); // 报修时间
    private String repairman; // 报修人姓名
    private String phoneNumber; // 报修人手机号
    private String buildingId;
    private String labId;

    private List<FaultImage> faultImages; // 故障图片
    private Lab lab;
    private TrainingBuilding trainingBuilding;
}
