package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.ServiceToDoctorRequestDTO;
import com.hackacode.clinica.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorService {

    DoctorDTO save(DoctorDTO doctorDTO);
    DoctorDTO findById(Long id);
    DoctorDTO update(Doctor doctor);
    void deleteById(Long id);
    Page<DoctorDTO> findAll(Pageable pageable);
    void addWorkingHour(Long doctorId, WorkingHourDTO workingHourDTO);
    void deleteWorkingHour(Long doctorId, Long workingHourId);
    List<Doctor> findAvailableDoctors(LocalDateTime start, LocalDateTime end, Long serviceId);
    void addService(Long doctorId, ServiceToDoctorRequestDTO serviceToDoctorRequestDTO);
    void removeService(Long doctorId, Long serviceId);
    Page<DoctorDTO> search(String name, Long specialityId, Pageable pageable);
}
