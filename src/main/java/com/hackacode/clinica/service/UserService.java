package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.UserResponseDTO;
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
}
