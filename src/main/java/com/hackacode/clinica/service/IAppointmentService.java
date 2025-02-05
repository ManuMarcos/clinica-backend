package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.AppointmentRequestDTO;
import com.hackacode.clinica.dto.AppointmentResponseDTO;
import com.hackacode.clinica.model.Patient;

import java.time.LocalDateTime;

public interface IAppointmentService {

    AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO);
    AppointmentResponseDTO update(AppointmentRequestDTO appointmentRequestDTO);
    boolean isPatientAvailable(Patient patient, LocalDateTime startTime, LocalDateTime endTime);
}
