package com.hackacode.clinica.dto.appointment;

import com.hackacode.clinica.dto.patient.PatientResponseDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
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
    private PatientResponseDTO patient;
    private ServiceResponseDTO service;
}
