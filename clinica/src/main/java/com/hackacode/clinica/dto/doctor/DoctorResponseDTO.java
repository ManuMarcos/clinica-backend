package com.hackacode.clinica.dto.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.model.Role;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DoctorResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String dni;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private Role role;

    private BigDecimal salary;

    private String speciality;

    private List<WorkingHourDTO> workingHours;

    private List<ServiceResponseDTO> services;
}
