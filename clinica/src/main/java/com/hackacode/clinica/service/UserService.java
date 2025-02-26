package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.user.UserDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.User;
import com.hackacode.clinica.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public UserDTO save(UserDTO userDTO) {
        return null;
    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        var users = userRepository.findAll(pageable);
        List<UserResponseDTO> usersDTO = new ArrayList<>();

        for(var user : users) {
            usersDTO.add(UserResponseDTO.builder()
                            .name(user.getName())
                            .dni(user.getDni())
                            .id(user.getId())
                            .email(user.getEmail())
                            .surname(user.getSurname())
                            .role(user.getRole())
                            .birthDate(user.getBirthDate())
                            .build()
            );
        }
        return new PageImpl<>(usersDTO, pageable, usersDTO.size());
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
                .birthDate(user.getBirthDate())
                .build();
    }

    @Override
    public void validateUniqueConstraints(UserDTO userDTO) {
        if(userRepository.existsByDni(userDTO.getDni())){
            throw new IllegalArgumentException("Dni is already in use");
        }
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new IllegalArgumentException("Email is already in use");
        }
    }

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + id + " does not exist")
        );
    }


}
