package com.api.portofolio.controllers;

import com.api.portofolio.models.GenericResponse;
import com.api.portofolio.models.LoginReq;
import com.api.portofolio.models.RegisterReq;
import com.api.portofolio.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization API's", description = "APIs for authorization of users")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Create a new user", description = "Add a new user to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signUp(
            @Valid
            @RequestBody RegisterReq registerReq) {
        GenericResponse genericResponse = authService.signUp(registerReq);
        return ResponseEntity.status(genericResponse.getStatusCode()).body(genericResponse);
    }

    @Operation(summary = "Sign In", description = "Sign In for Registered User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/signin")
    public ResponseEntity<GenericResponse> signIn(
            @Valid
            @RequestBody LoginReq loginReq) {
        GenericResponse genericResponse = authService.signIn(loginReq);
        return ResponseEntity.status(genericResponse.getStatusCode()).body(genericResponse);
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<GenericResponse> refreshToken(@RequestBody ReqRes refreshTokenRequest) {
//        return authService.refreshToken(refreshTokenRequest);
//    }
}
