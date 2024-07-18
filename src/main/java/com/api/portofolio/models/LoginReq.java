package com.api.portofolio.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Schema(description = "User password", example = "password123")
    private String password;
}
