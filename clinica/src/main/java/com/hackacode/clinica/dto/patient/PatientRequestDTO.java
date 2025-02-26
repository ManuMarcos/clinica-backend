package com.hackacode.clinica.dto.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceRequestDTO;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class PatientRequestDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String dni;
    @JsonProperty("birth_date")
    private LocalDate birthDate;
    @JsonProperty("health_insurance")
    private HealthInsuranceRequestDTO healthInsurance;
}
