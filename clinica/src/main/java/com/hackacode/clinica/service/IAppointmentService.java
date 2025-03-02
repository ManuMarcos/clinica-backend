package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.appointment.AppointmentRequestDTO;
import com.hackacode.clinica.dto.appointment.AppointmentResponseDTO;
import com.hackacode.clinica.dto.appointment.AppointmentUpdateDTO;
import com.hackacode.clinica.dto.DoctorAvailabilityDTO;
import com.hackacode.clinica.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    Page<AppointmentResponseDTO> findAll(Pageable pageable);
    AppointmentResponseDTO save(AppointmentRequestDTO appointmentRequestDTO);
    AppointmentResponseDTO update(Long appoitmentId, AppointmentUpdateDTO appointmentUpdateDTO);
    List<DoctorAvailabilityDTO> getAvailabilityForService(Long serviceId, LocalDate from, LocalDate to);
    void deleteById(Long id);
    public byte[] exportToPdf(Long appointmentId);
}
