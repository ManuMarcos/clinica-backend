package com.hackacode.clinica.mapper;


import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IUserMapper {


    UserResponseDTO toResponseDTO(User user);

    User toEntity(UserRequestDTO userRequestDTO);

}
