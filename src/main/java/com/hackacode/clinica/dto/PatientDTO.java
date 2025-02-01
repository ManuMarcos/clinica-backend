package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.HealthInsurance;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
public class PatientDTO {
    private Long id;
    private String dni;
    private String name;
    private String surname;
    private String email;
    @JsonProperty("birth_date")
    private LocalDate birthDate;
    @JsonProperty("health_insurance")
    private HealthInsuranceDTO healthInsurance;

    public static PatientDTO from(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .dni(patient.getDni())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .birthDate(patient.getBirthDate())
                .healthInsurance(HealthInsuranceDTO.from(patient.getHealthInsurance()))
                .build();
    }

}
