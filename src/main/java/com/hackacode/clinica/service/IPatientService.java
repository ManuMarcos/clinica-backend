package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatientService {
    PatientDTO save(PatientDTO patientDTO);
    Page<PatientDTO> findAll(Pageable pageable);
    PatientDTO findById(Long id);
    PatientDTO update(PatientDTO patientDTO);
    void deleteById(Long id);
}
