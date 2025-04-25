package com.liuqi.machineroomrepairsystem.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @NotBlank
    private String id;

    @NotBlank
    private String username;

    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phoneNumber;

    @Min(1)
    @Max(2)
    private byte role;
}
