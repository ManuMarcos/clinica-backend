package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.authentication.AccessTokenDTO;
import com.hackacode.clinica.dto.authentication.LoginRequestDTO;
import com.hackacode.clinica.dto.authentication.LoginResponseDTO;
import com.hackacode.clinica.model.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IAuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    AccessTokenDTO refreshToken(@NotNull final String authentication);
    boolean isPatient();
    boolean isAdmin();
    List<Role> getCurrentUserRoles();
    Long getCurrentUserId();
    String getCurrentUserEmail();
}
