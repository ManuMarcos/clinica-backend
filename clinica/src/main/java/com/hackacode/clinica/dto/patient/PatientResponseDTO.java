package com.hackacode.clinica.dto.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceResponseDTO;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.model.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PatientResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private HealthInsuranceResponseDTO healthInsurance;
}
