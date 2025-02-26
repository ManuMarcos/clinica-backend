package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.patient.PatientRequestDTO;
import com.hackacode.clinica.dto.patient.PatientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatientService {
    PatientResponseDTO save(PatientRequestDTO patientRequestDTO);
    Page<PatientResponseDTO> findAll(Pageable pageable);
    PatientResponseDTO findById(Long id);
    PatientResponseDTO update(PatientRequestDTO patientRequestDTO);
    void deleteById(Long id);
}
