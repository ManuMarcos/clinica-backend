package com.hackacode.clinica.dto.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.appointment.AppointmentResponseDTO;
import com.hackacode.clinica.dto.healthInsurance.HealthInsuranceResponseDTO;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.model.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private HealthInsuranceResponseDTO healthInsurance;
    private List<AppointmentResponseDTO> appointments;
}
