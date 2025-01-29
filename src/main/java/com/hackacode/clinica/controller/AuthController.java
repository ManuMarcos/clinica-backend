package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.LoginRequestDTO;
import com.hackacode.clinica.dto.RegisterRequestDTO;
import com.hackacode.clinica.dto.TokenResponseDTO;
import com.hackacode.clinica.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Register a new Patient, Doctor or Admin")
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        final TokenResponseDTO tokenResponseDTO = this.authService.register(registerRequestDTO);
        return ResponseEntity.ok(tokenResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        final TokenResponseDTO tokenResponseDTO = this.authService.login(loginRequestDTO);
        return ResponseEntity.ok(tokenResponseDTO);
    }


}
