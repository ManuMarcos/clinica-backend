package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.DoctorResponseDTO;
import com.hackacode.clinica.model.Doctor;

import java.util.List;

public interface IDoctorService {

    DoctorResponseDTO findById(Long id);
    DoctorResponseDTO save(Doctor doctor);
    DoctorResponseDTO update(Doctor doctor);
    DoctorResponseDTO deleteById(Long id);
    List<DoctorResponseDTO> findAll();
}
