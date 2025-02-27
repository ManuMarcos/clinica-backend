package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.ServiceToDoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.dto.workingHour.WorkingHourRequestDTO;
import com.hackacode.clinica.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorService {

    DoctorResponseDTO save(DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO findById(Long id);
    DoctorResponseDTO update(DoctorRequestDTO doctorRequestDTO);
    void deleteById(Long id);
    Page<DoctorResponseDTO> findAll(String name, Long specialityId, Pageable pageable);
    void addWorkingHour(Long doctorId, WorkingHourRequestDTO workingHourRequestDTO);
    void deleteWorkingHour(Long doctorId, Long workingHourId);
    List<DoctorResponseDTO> findAvailableDoctors(LocalDateTime start, LocalDateTime end, Long serviceId);
    void addService(Long doctorId, Long serviceId);
    void removeService(Long doctorId, Long serviceId);
    boolean ifDoctorProvidesService(Long doctorId, Long serviceId);
    boolean ifDoctorWorksThisDayAtTime(Long doctorId, LocalDateTime timeFrom, LocalDateTime timeTo);
}
