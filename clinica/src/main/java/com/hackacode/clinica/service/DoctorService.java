package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.ServiceToDoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.dto.user.UserDTO;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.exception.UnauthorizedException;
import com.hackacode.clinica.mapper.IDoctorMapper;
import com.hackacode.clinica.mapper.IServiceMapper;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.model.Speciality;
import com.hackacode.clinica.repository.IDoctorRepository;
import com.hackacode.clinica.repository.IServiceRepository;
import com.hackacode.clinica.repository.ISpecialityRepository;
import com.hackacode.clinica.repository.IWorkingHourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService implements IDoctorService {

    private final IDoctorRepository doctorRepository;
    private final IWorkingHourRepository workingHourRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final ISpecialityRepository specialityRepository;
    private final IDoctorMapper doctorMapper;
    private final IServiceMapper serviceMapper;
    private final IUserService userService;
    private final IServiceRepository serviceRepository;

    @Override
    public DoctorResponseDTO save(DoctorRequestDTO doctorDTO) {
        //TODO: To refactor
        userService.validateUniqueConstraints(UserDTO.builder()
                .email(doctorDTO.getEmail())
                .dni(doctorDTO.getDni())
                .build()
        );
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
        var doctorDTOS = doctors.stream().map(doctorMapper::toDTO).toList();
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
        isAllowedToModify(doctorId);
        var doctor = this.getDoctorById(doctorId);
        var wh = workingHourRepository.findById(workingHourId).orElseThrow(
                () -> new ResourceNotFoundException("WorkingHour not found")
        );
        if (!doctor.removeWorkingHour(wh)) {
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
    public void addService(Long doctorId, ServiceToDoctorRequestDTO serviceToDoctorRequestDTO) {
        isAllowedToModify(doctorId);
        var doctor = this.getDoctorById(doctorId);
        var service = this.getServiceById(serviceToDoctorRequestDTO.getServiceId());
        if(doctor.getServices().contains(service)){
            throw new BadRequestException("This service is already assigned to this doctor");
        };
        doctor.addService(service);
        doctorRepository.save(doctor);
    }

    @Override
    public void removeService(Long doctorId, Long serviceId) {
        isAllowedToModify(doctorId);
        var doctor = this.getDoctorById(doctorId);
        var service = this.getServiceById(serviceId);
        if(!doctor.removeService(service)){
            throw new ResourceNotFoundException("Service not found for this doctor");
        };
        doctorRepository.save(doctor);
    }

    @Override
    public Page<DoctorDTO> search(String name, Long specialityId, Pageable pageable) {
        Page<Doctor> doctors;
        if(name != null && specialityId != null) {
            doctors = doctorRepository.findBySpecialityIdAndNameContaining(specialityId, name, pageable);
        }
        else if(name != null){
             doctors = doctorRepository.findByNameContaining(name, pageable);
        }
        else if(specialityId != null){
            doctors = doctorRepository.findBySpecialityId(specialityId, pageable);
        }
        else{
            doctors = doctorRepository.findAll(pageable);
        }
        return doctors.map(doctorMapper::toDTO);
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
    public void deleteById(Long id) {
    }

    private Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Doctor with ID " + id + " does not exist"));
    }

    private com.hackacode.clinica.model.Service getServiceById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Service with ID " + id + " does not exist"));
    }

    private void isAllowedToModify(Long doctorId){
        if(!authService.getCurrentUserRoles().contains(Role.ADMIN) &&
                !doctorId.equals(authService.getCurrentUserId())) {
            throw new UnauthorizedException("You are not allowed to modify this doctor.");
        }
    }
}
