package com.hackacode.clinica.mapper;

import com.hackacode.clinica.dto.PatientDTO;
import com.hackacode.clinica.model.HealthInsurance;
import com.hackacode.clinica.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientMapper {

    private final HealthInsuranceMapper healthInsuranceMapper;

    public PatientDTO toDTO(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .dni(patient.getDni())
                .email(patient.getEmail())
                .birthDate(patient.getBirthDate())
                .healthInsurance(healthInsuranceMapper.toDTO(patient.getHealthInsurance()))
                .build();
    }

    public Patient toEntity(PatientDTO patientDTO) {
        return Patient.builder()
                .name(patientDTO.getName())
                .surname(patientDTO.getSurname())
                .dni(patientDTO.getDni())
                .email(patientDTO.getEmail())
                .birthDate(patientDTO.getBirthDate())
                .healthInsurance(healthInsuranceMapper.toEntity(patientDTO.getHealthInsurance()))
                .build();
    }
}
