package com.hackacode.clinica.mapper;


import com.hackacode.clinica.dto.speciality.SpecialityRequestDTO;
import com.hackacode.clinica.dto.speciality.SpecialityResponseDTO;
import com.hackacode.clinica.model.Speciality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ISpecialityMapper {

    SpecialityResponseDTO toResponseDTO(Speciality speciality);

    Speciality toEntity(SpecialityRequestDTO specialityRequestDTO);
}
