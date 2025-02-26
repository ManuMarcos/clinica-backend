package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.authentication.AccessTokenDTO;
import com.hackacode.clinica.dto.authentication.LoginRequestDTO;
import com.hackacode.clinica.dto.authentication.LoginResponseDTO;
import com.hackacode.clinica.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User Authentication",
            description = "This endpoint allows users to authenticate using their credentials (username and password) to receive a JWT token, which will be used for accessing other API resources.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful. JWT token returned."),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials. Username or password does not match."),
            @ApiResponse(responseCode = "400", description = "Bad request. The request body is invalid.")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(this.authService.login(loginRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDTO> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                                         final String authentication) {
        return ResponseEntity.ok(this.authService.refreshToken(authentication));
    }


}
