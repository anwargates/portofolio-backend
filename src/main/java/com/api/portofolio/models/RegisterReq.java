package com.api.portofolio.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReq {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Schema(description = "Name of the user", example = "John Doe")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = "USER|ADMIN", message = "Role must be either USER or ADMIN")
    @Schema(description = "Role of the user", example = "USER")
    private String role;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Schema(description = "Password of the user", example = "P@ssw0rd")
    private String password;
}
