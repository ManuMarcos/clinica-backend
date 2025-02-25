package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.UserDTO;
import com.hackacode.clinica.dto.UserResponseDTO;
import com.hackacode.clinica.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    UserDTO save(UserDTO userDTO);
    Page<UserResponseDTO> findAll(Pageable pageable);
    UserResponseDTO findById(Long id);
    void validateUniqueConstraints(UserDTO userDTO);
}
