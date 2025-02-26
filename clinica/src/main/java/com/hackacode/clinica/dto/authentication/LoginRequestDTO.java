package com.hackacode.clinica.dto.authentication;

public record LoginRequestDTO(
        String email,
        String password
) {
}
