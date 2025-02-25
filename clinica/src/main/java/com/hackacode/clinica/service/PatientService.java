package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.PatientDTO;
import com.hackacode.clinica.dto.UserDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.PatientMapper;
import com.hackacode.clinica.model.Patient;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.repository.IPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        //TODO: To refactor
        userService.validateUniqueConstraints(UserDTO.builder()
                .email(patientDTO.getEmail())
                .dni(patientDTO.getDni())
                .build()
        );
        var patient = patientMapper.toEntity(patientDTO);
        patient.setRole(Role.PATIENT);
        patient.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    @Override
    public Page<PatientDTO> findAll(Pageable pageable) {
        var patients = patientRepository.findAll(pageable);
        List<PatientDTO> patientDTOS = patients.stream().map(patientMapper::toDTO).toList();
        return new PageImpl<>(patientDTOS, pageable, patientDTOS.size());
    }

    @Override
    public PatientDTO findById(Long id) {
        var patient =  patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found with id " + id)
        );
        return patientMapper.toDTO(patient);
    }

    @Override
    public PatientDTO update(PatientDTO patientDTO) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        var patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found with id " + id)
        );
        patientRepository.delete(patient);
    }


}
