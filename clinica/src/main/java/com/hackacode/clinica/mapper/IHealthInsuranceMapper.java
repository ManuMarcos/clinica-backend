package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceRequestDTO;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceResponseDTO;
import com.hackacode.clinica.model.HealthInsurance;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface IHealthInsuranceMapper {
    HealthInsuranceResponseDTO toResponseDTO(HealthInsurance healthInsurance);
    HealthInsurance toEntity(HealthInsuranceRequestDTO healthInsuranceRequestDTO);
}
