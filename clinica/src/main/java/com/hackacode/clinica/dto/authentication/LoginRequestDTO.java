package com.hackacode.clinica.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LoginRequestDTO {
    String email;
    String password;
}
