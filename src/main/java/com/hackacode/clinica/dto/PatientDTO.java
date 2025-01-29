package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Role;

import java.time.LocalDate;

public class PatientDTO extends UserResponseDTO{

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String dni;
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
    private Role role;
    private HealthInsuranceDTO healthInsurance;
    //TODO: Faltan dos listas (consultations y appointments)

}
