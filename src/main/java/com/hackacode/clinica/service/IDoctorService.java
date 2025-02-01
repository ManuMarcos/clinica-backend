package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.DoctorDTO;
import com.hackacode.clinica.dto.WorkingHourDTO;
import com.hackacode.clinica.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDoctorService {

    DoctorDTO findById(Long id);
    DoctorDTO update(Doctor doctor);
    DoctorDTO deleteById(Long id);
    Page<DoctorDTO> findAll(Pageable pageable);
    void addWorkingHour(Long doctorId, WorkingHourDTO workingHourDTO);
}
