package org.tolking.animeharbor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "Username can't be blank")
    @Size(min = 3, max = 30, message = "Length should be between 3 and 30")
    private String userName;

    @Email(message = "Enter valid email", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Pattern(message = "Minimum 8 characters, at least one capital letter and small letter and one digit",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$")
    private String password;
}
