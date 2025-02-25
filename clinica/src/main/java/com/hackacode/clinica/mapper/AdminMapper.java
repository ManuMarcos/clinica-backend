package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.AdminDTO;
import com.hackacode.clinica.model.Admin;
import com.hackacode.clinica.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {

    public AdminDTO toDTO(Admin admin) {
        return AdminDTO.builder()
                .id(admin.getId())
                .name(admin.getName())
                .surname(admin.getSurname())
                .email(admin.getEmail())
                .dni(admin.getDni())
                .role(Role.ADMIN)
                .birthDate(admin.getBirthDate())
                .build();
    }

    public Admin toEntity(AdminDTO adminDTO) {
        return Admin.builder()
                .name(adminDTO.getName())
                .surname(adminDTO.getSurname())
                .email(adminDTO.getEmail())
                .dni(adminDTO.getDni())
                .birthDate(adminDTO.getBirthDate())
                .role(Role.ADMIN)
                .position(adminDTO.getPosition())
                .build();
    }



}
