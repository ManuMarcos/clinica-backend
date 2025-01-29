package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.PatientDTO;
import com.hackacode.clinica.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface IPatientService {

    PatientDTO save(PatientDTO patientDTO);
    List<PatientDTO> findAll(Pageable pageable);

}
