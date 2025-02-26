package com.hackacode.clinica.dto.appointment;

import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.dto.patient.PatientResponseDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.model.AppointmentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private DoctorResponseDTO doctor;
    private PatientResponseDTO patient;
    private ServiceResponseDTO service;
}
