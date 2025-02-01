package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.DoctorDTO;
import com.hackacode.clinica.dto.WorkingHourDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.repository.IDoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final IDoctorRepository doctorRepository;


    @Override
    public Page<DoctorDTO> findAll(Pageable pageable) {
        var doctors = doctorRepository.findAll(pageable);
        var doctorDTOS = new ArrayList<DoctorDTO>();

        for(Doctor doctor : doctors) {
            doctorDTOS.add(DoctorDTO.from(doctor));
        }

        return new PageImpl<>(doctorDTOS, pageable, doctorDTOS.size());
    }

    @Override
    public void addWorkingHour(Long doctorId, WorkingHourDTO workingHourDTO) {
        var doctor = this.getDoctorById(doctorId);
        doctor.addWorkingHour(WorkingHourDTO.toEntity(workingHourDTO));
        doctorRepository.save(doctor);
    }


    @Override
    public DoctorDTO findById(Long id) {
        var doctor = this.getDoctorById(id);
        return DoctorDTO.from(doctor);
    }


    @Override
    public DoctorDTO update(Doctor doctor) {
        return null;
    }

    @Override
    public DoctorDTO deleteById(Long id) {
        return null;
    }

    private Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Doctor with ID " + id + " does not exist"));
    }
}
