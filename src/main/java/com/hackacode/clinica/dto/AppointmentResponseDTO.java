package com.hackacode.clinica.dto;

import com.hackacode.clinica.model.AppointmentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private DoctorBasicDTO doctor;
    private PatientDTO patient;
    private ServiceDTO service;

}
