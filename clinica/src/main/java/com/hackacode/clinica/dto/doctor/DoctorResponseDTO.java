package com.hackacode.clinica.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.dto.workingHour.WorkingHourResponseDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorResponseDTO {

    private Long id;
    private UserResponseDTO user;
    private String speciality;
    @JsonProperty("working_hours")
    private List<WorkingHourResponseDTO> workingHours;
    private List<ServiceResponseDTO> services;
    private Integer appointmentDuration;
}
