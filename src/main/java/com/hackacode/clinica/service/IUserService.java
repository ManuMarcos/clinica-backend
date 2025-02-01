package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IUserService {

    Page<UserResponseDTO> findAll(Pageable pageable);

    UserResponseDTO findById(Long id);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}
