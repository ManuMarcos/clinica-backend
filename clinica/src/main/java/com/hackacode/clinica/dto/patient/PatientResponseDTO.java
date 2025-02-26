package com.hackacode.clinica.dto.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceResponseDTO;
import com.hackacode.clinica.model.Role;

import java.time.LocalDate;

public class PatientResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String dni;
    @JsonProperty("birth_date")
    private LocalDate birthDate;
    private Role role;
    private HealthInsuranceResponseDTO healthInsurance;
}
