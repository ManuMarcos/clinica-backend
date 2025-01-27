package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {

    List<UserResponseDTO> findAll();

}
