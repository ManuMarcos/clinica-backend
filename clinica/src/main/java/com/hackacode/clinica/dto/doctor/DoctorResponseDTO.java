package com.hackacode.clinica.dto.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.dto.workingHour.WorkingHourResponseDTO;
import com.hackacode.clinica.model.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class DoctorResponseDTO {

    private Long id;
    private UserRequestDTO user;
    private String speciality;
    @JsonProperty("working_hours")
    private List<WorkingHourResponseDTO> workingHours;
    private List<ServiceResponseDTO> services;
}
