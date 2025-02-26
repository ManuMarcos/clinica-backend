package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User save(UserRequestDTO userRequestDTO);
    Page<UserResponseDTO> findAll(Pageable pageable);
    UserResponseDTO findById(Long id);
    void validateUniqueConstraints(UserRequestDTO userRequestDTO);
}
