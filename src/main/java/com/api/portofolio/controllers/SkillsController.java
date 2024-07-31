package com.api.portofolio.controllers;

import com.api.portofolio.models.GenericResponse;
import com.api.portofolio.models.RegisterReq;
import com.api.portofolio.models.SkillsReq;
import com.api.portofolio.services.SkillsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/skills")
@Tag(name = "Skills API's", description = "APIs for managing skills")
public class SkillsController {

    @Autowired
    private SkillsService skillsService;

    @Operation(summary = "Create a new skill", description = "Add a new skill to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Skill created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse> addSkill(
            @Valid
            @ModelAttribute SkillsReq skillsReq,
            @RequestPart(value = "icon", required = false) MultipartFile icon) {
        GenericResponse genericResponse = skillsService.doAddSkill(skillsReq, icon);
        return ResponseEntity.status(genericResponse.getStatusCode()).body(genericResponse);
    }

//    @Operation(summary = "Sign In", description = "Sign In for Registered User")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful login",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Invalid request",
//                    content = @Content(mediaType = "application/json")),
//            @ApiResponse(responseCode = "401", description = "Unauthorized",
//                    content = @Content(mediaType = "application/json")),
//            @ApiResponse(responseCode = "500", description = "Internal server error",
//                    content = @Content(mediaType = "application/json"))
//    })
//    @PostMapping("/signin")
//    public ResponseEntity<GenericResponse> signIn(
//            @Valid
//            @RequestBody LoginReq loginReq) {
//        return authService.signIn(loginReq);
//    }
}
