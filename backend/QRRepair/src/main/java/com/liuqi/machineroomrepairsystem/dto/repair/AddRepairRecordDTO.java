package com.liuqi.machineroomrepairsystem.dto.repair;

import com.liuqi.machineroomrepairsystem.mapper.LabMapper;
import com.liuqi.machineroomrepairsystem.mapper.TrainingBuildingMapper;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * 由于一些原因，这里变为 二维码报修的DTO
 */
@Data
public class AddRepairRecordDTO {

    @NotBlank(message = "报修实训楼不能为空")
    private String buildingName;

    @NotBlank(message = "报修实训室不能为空")
    private String labName;

    @Min(value = 1,message = "报修设备编号错误")
    private int equipmentNo; // 报修设备编号

    @NotBlank(message = "故障描述不能为空")
    private String description;

    @NotNull(message = "没有上传故障图片")
    @Size(min = 1,max = 4,message = "至多上传4张图片")
    MultipartFile[] files; // 故障图片。(单张图片大小不能超过2MB,且至多上传 4张图片)

    @NotBlank(message = "报修人不能为空")
    private String repairman; // 报修人姓名

    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "请输入正确的手机号")
    private String phoneNumber; // 报修人手机号

}
