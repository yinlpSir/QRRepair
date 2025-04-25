package com.liuqi.machineroomrepairsystem.dto.repair;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 小程序报修DTO
 */
@Data
public class AppletRepairRecordDTO {
    @NotBlank(message = "报修实训楼不能为空")
    private String buildingName;

    @NotBlank(message = "报修实训室不能为空")
    private String labName;

    @Min(value = 1,message = "报修设备编号错误")
    private int equipmentNo; // 报修设备编号

    @NotBlank(message = "故障描述不能为空")
    private String description;

    @NotBlank(message = "报修人不能为空")
    private String repairman; // 报修人姓名

    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "请输入正确的手机号")
    private String phoneNumber; // 报修人手机号
}
