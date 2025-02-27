package com.hackacode.clinica.dto.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceRequestDTO;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceResponseDTO;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class PatientRequestDTO {
    private UserRequestDTO user;
    @JsonProperty("health_insurance")
    private HealthInsuranceRequestDTO healthInsurance;
}
