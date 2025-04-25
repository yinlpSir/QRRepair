package com.liuqi.machineroomrepairsystem.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterDTO {
    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 6,max = 18)
    private String password;

    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phoneNumber;

    private String email;
}
