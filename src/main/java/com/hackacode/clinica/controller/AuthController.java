package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.LoginRequestDTO;
import com.hackacode.clinica.dto.RegisterRequestDTO;
import com.hackacode.clinica.dto.TokenResponseDTO;
import com.hackacode.clinica.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user",
            description = "Register a new Patient, Doctor or Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful. JWT token returned."),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials. Username or password does not match."),
            @ApiResponse(responseCode = "400", description = "Bad request. The request body is invalid.")
    })
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        final TokenResponseDTO tokenResponseDTO = this.authService.register(registerRequestDTO);
        return ResponseEntity.ok(tokenResponseDTO);
    }

    @Operation(summary = "User Authentication",
            description = "This endpoint allows users to authenticate using their credentials (username and password) to receive a JWT token, which will be used for accessing other API resources.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful. JWT token returned."),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials. Username or password does not match."),
            @ApiResponse(responseCode = "400", description = "Bad request. The request body is invalid.")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        final TokenResponseDTO tokenResponseDTO = this.authService.login(loginRequestDTO);
        return ResponseEntity.ok(tokenResponseDTO);
    }


}
