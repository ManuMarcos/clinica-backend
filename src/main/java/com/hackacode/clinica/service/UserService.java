package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.UserResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.User;
import com.hackacode.clinica.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public List<UserResponseDTO> findAll() {
        var users = userRepository.findAll();
        List<UserResponseDTO> usersDTO = new ArrayList<>();

        for(var user : users) {
            usersDTO.add(UserResponseDTO.builder()
                            .name(user.getName())
                            .dni(user.getDni())
                            .id(user.getId())
                            .email(user.getEmail())
                            .surname(user.getSurname())
                            .role(user.getRole())
                            .dateOfBirth(user.getDateOfBirth())
                            .build()
            );
        }
        return usersDTO;
    }

    @Override
    public UserResponseDTO findById(Long id) {
        var user = this.getById(id);
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .dni(user.getDni())
                .surname(user.getSurname())
                .role(user.getRole())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

    @Override
    public boolean existsByDni(String dni) {
        return userRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + id + " does not exist")
        );
    }

}
