package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.HealthInsuranceDTO;
import com.hackacode.clinica.model.HealthInsurance;
import org.springframework.stereotype.Component;

@Component
public class HealthInsuranceMapper {

    public HealthInsuranceDTO toDTO(HealthInsurance healthInsurance) {
        if(healthInsurance == null) {return null;}
        return HealthInsuranceDTO.builder()
                .name(healthInsurance.getName())
                .number(healthInsurance.getNumber())
                .plan(healthInsurance.getPlan())
                .build();
    }

    public HealthInsurance toEntity(HealthInsuranceDTO healthInsuranceDTO) {
        if(healthInsuranceDTO == null) {return null;}
        return HealthInsurance.builder()
                .name(healthInsuranceDTO.getName())
                .plan(healthInsuranceDTO.getPlan())
                .number(healthInsuranceDTO.getNumber())
                .build();
    }

}
