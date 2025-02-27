package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.admin.AdminRequestDTO;
import com.hackacode.clinica.dto.admin.AdminResponseDTO;
import com.hackacode.clinica.model.Admin;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {IUserMapper.class})
public interface IAdminMapper {

    AdminResponseDTO toResponseDTO(Admin admin);
    
    Admin toEntity(AdminRequestDTO adminRequestDTO);
}
