package com.liuqi.machineroomrepairsystem.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 故障图片
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaultImage {
    private String id;
    @JsonIgnore
    private InputStream image;
    private String imageToString; // Base64 encoded image
    private String repairRecordId;
}
