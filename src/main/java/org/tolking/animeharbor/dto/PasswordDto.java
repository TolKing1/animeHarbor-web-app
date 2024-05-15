package org.tolking.animeharbor.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordDto {
    @Pattern(message = "Minimum 8 characters, at least one capital letter and small letter and one digit",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$")
    private String password;

    @Pattern(message = "Minimum 8 characters, at least one capital letter and small letter and one digit",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$")
    private String confirmPassword;
}
