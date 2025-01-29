package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.DoctorResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.repository.IDoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final IDoctorRepository doctorRepository;

    @Override
    public DoctorResponseDTO findById(Long id) {
        var doctor = this.getDoctorById(id);
        return DoctorResponseDTO.builder()
                .id(doctor.getId())
                .dni(doctor.getDni())
                .email(doctor.getEmail())
                .name(doctor.getName())
                .role(doctor.getRole())
                .salary(doctor.getSalary())
                .speciality(doctor.getSpeciality().getName())
                .surname(doctor.getSurname())
                .dateOfBirth(doctor.getDateOfBirth())
                .build();
    }

    @Override
    public DoctorResponseDTO save(Doctor doctor) {
        return null;
    }

    @Override
    public DoctorResponseDTO update(Doctor doctor) {
        return null;
    }

    @Override
    public DoctorResponseDTO deleteById(Long id) {
        return null;
    }

    @Override
    public List<DoctorResponseDTO> findAll() {
        return List.of();
    }

    private Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Doctor with ID " + id + " does not exist"));
    }
}
