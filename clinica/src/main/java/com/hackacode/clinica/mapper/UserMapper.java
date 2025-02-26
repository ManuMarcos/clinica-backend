package com.hackacode.clinica.mapper;


import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .dni(user.getDni())
                .role(user.getRole())
                .build();
    }


}
