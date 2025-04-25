package com.liuqi.machineroomrepairsystem.dto.trainingBuilding;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTrainingBuildingDTO {
    @NotBlank
    private String id;
    @NotBlank
    private String buildingName;
}
