package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.DoctorDTO;
import com.hackacode.clinica.dto.WorkingHourDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.DoctorMapper;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.model.Speciality;
import com.hackacode.clinica.repository.IDoctorRepository;
import com.hackacode.clinica.repository.ISpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final IDoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ISpecialityRepository specialityRepository;
    private final DoctorMapper doctorMapper;
    private final UserService userService;

    @Override
    public DoctorDTO save(DoctorDTO doctorDTO) {
        userService.validateUniqueConstraints(doctorDTO);
        if (doctorDTO.getSpecialityId() == null) {
            throw new IllegalArgumentException("Doctors must have speciality");
        }
        Speciality speciality = specialityRepository.findById(doctorDTO.getSpecialityId())
                .orElseThrow(() -> new ResourceNotFoundException("Speciality not found"));
        Doctor doctor = doctorMapper.toEntity(doctorDTO, speciality);
        doctor.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        doctor.setRole(Role.DOCTOR);
        return doctorMapper.toDTO(doctorRepository.save(doctor));
    }

    @Override
    public Page<DoctorDTO> findAll(Pageable pageable) {
        var doctors = doctorRepository.findAll(pageable);
        var doctorDTOS = new ArrayList<DoctorDTO>();

        for(Doctor doctor : doctors) {
            doctorDTOS.add(doctorMapper.toDTO(doctor));
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
    public void deleteWorkingHour(Long doctorId, Long workingHourId) {
        var doctor = this.getDoctorById(doctorId);
        boolean removed = doctor.getWorkingHours().removeIf((wh) -> wh.getWorkingHourId().equals(workingHourId));
        if(!removed) {
            throw new ResourceNotFoundException("Working hour not found for this doctor, doctorId: " + doctorId + ", workingHourId: " + workingHourId);
        }
        doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> findAvailableDoctors(LocalDateTime start, LocalDateTime end, Long serviceId) {
        return doctorRepository.findAvailableDoctors(start.getDayOfWeek(),start.toLocalTime(),
                        serviceId, start, end);
    }

    @Override
    public DoctorDTO findById(Long id) {
        var doctor = this.getDoctorById(id);
        return doctorMapper.toDTO(doctor);
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
