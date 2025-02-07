package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.AppointmentRequestDTO;
import com.hackacode.clinica.dto.AppointmentResponseDTO;
import com.hackacode.clinica.dto.DoctorAvailabilityDTO;
import com.hackacode.clinica.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO);
    AppointmentResponseDTO update(AppointmentRequestDTO appointmentRequestDTO);
    boolean isPatientAvailable(Patient patient, LocalDateTime startTime, LocalDateTime endTime);
    List<DoctorAvailabilityDTO> getAvailabilityForService(Long serviceId, LocalDate from, LocalDate to);

}
