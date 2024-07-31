package com.api.portofolio.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillsReq {

    @NotNull(message = "User ID is mandatory")
    @Schema(description = "Id of the user", example = "0")
    private Long userId;

    @NotBlank(message = "Title is mandatory")
    @Schema(description = "Name of the skill", example = "Spring Boot")
    private String title;

    @Schema(description = "Describe the skill", example = "I liked this")
    private String description;

//    @NotBlank(message = "Icon is mandatory")
//    @Schema(description = "Base64 string of skill icon")
//    private String icon;
}
