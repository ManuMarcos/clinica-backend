package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.PatientDTO;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.repository.IPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;

    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        return null;
    }

    @Override
    public List<PatientDTO> findAll(Pageable pageable) {
        return List.of();
    }

    private Patient dtoToEntity(){

    }
}
