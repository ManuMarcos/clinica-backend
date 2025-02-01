package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.PatientDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.repository.IPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;

    @Override
    public Page<PatientDTO> findAll(Pageable pageable) {
        var patients = patientRepository.findAll(pageable);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for(var patient : patients) {
            patientDTOS.add(PatientDTO.from(patient));
        }
        return new PageImpl<>(patientDTOS, pageable, patientDTOS.size());
    }

    @Override
    public PatientDTO findById(Long id) {
        var patient =  patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found with id " + id)
        );
        return PatientDTO.from(patient);
    }

    @Override
    public PatientDTO update(PatientDTO patientDTO) {
        return null;
    }


}
