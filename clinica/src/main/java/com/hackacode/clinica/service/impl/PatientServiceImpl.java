package com.hackacode.clinica.service.impl;

import com.hackacode.clinica.dto.patient.PatientRequestDTO;
import com.hackacode.clinica.dto.patient.PatientResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.IPatientMapper;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.repository.IPatientRepository;
import com.hackacode.clinica.service.IPatientService;
import com.hackacode.clinica.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements IPatientService {

    private final IPatientRepository patientRepository;
    private final IPatientMapper patientMapper;
    private final IUserService userService;


    @Override
    public PatientResponseDTO save(PatientRequestDTO patientRequestDTO) {
        var savedUser = userService.save(patientRequestDTO.getUser(), Role.PATIENT);
        var patient = patientMapper.toEntity(patientRequestDTO);
        patient.setUser(savedUser);
        return patientMapper.toResponseDTO(patientRepository.save(patient));
    }

    @Override
    public Page<PatientResponseDTO> findAll(Pageable pageable) {
        var patients = patientRepository.findAll(pageable);
        List<PatientResponseDTO> patientDTOS = patients.stream().map(patientMapper::toResponseDTO).toList();
        return new PageImpl<>(patientDTOS, pageable, patientDTOS.size());
    }

    @Override
    public PatientResponseDTO findById(Long id) {
        return patientMapper.toResponseDTO(getPatientById(id));
    }

    @Override
    public PatientResponseDTO update(PatientRequestDTO patientDTO) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.delete(getPatientById(id));
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found with id " + id)
        );
    }


}
