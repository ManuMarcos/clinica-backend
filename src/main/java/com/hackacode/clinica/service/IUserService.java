package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {

    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Long id);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}
