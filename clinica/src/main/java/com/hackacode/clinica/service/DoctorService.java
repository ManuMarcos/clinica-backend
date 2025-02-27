package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.ServiceToDoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.dto.workingHour.WorkingHourRequestDTO;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.exception.UnauthorizedException;
import com.hackacode.clinica.mapper.IDoctorMapper;
import com.hackacode.clinica.mapper.IServiceMapper;
import com.hackacode.clinica.mapper.IWorkingHourMapper;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.model.Speciality;
import com.hackacode.clinica.repository.IDoctorRepository;
import com.hackacode.clinica.repository.IServiceRepository;
import com.hackacode.clinica.repository.ISpecialityRepository;
import com.hackacode.clinica.repository.IWorkingHourRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService implements IDoctorService {

    private final IDoctorRepository doctorRepository;
    private final IWorkingHourRepository workingHourRepository;
    private final AuthService authService;
    private final ISpecialityRepository specialityRepository;
    private final IDoctorMapper doctorMapper;
    private final IUserService userService;
    private final IServiceRepository serviceRepository;
    private final IWorkingHourMapper workingHourMapper;

    @Override
    public DoctorResponseDTO save(DoctorRequestDTO doctorRequestDTO) {
        var savedUser = userService.save(doctorRequestDTO.getUser(), Role.DOCTOR);
        if (doctorRequestDTO.getSpecialityId() == null) {
            throw new IllegalArgumentException("Doctors must have speciality");
        }
        var speciality = specialityRepository.findById(doctorRequestDTO.getSpecialityId())
                .orElseThrow(() -> new ResourceNotFoundException("Speciality not found"));
        Doctor doctor = doctorMapper.toEntity(doctorRequestDTO);
        doctor.setSpeciality(speciality);
        doctor.setUser(savedUser);
        return doctorMapper.toResponseDTO(doctorRepository.save(doctor));
    }

    @Override
    public Page<DoctorResponseDTO> findAll(String name, Long specialityId, Pageable pageable) {
        Page<Doctor> doctors;
        if(name != null && specialityId != null) {
            doctors = doctorRepository.findBySpecialityIdAndUser_NameContaining(specialityId, name, pageable);
        }
        else if(name != null){
            doctors = doctorRepository.findByUser_NameContainingIgnoreCase(name, pageable);
        }
        else if(specialityId != null){
            doctors = doctorRepository.findBySpecialityId(specialityId, pageable);
        }
        else{
            doctors = doctorRepository.findAll(pageable);
        }
        return doctors.map(doctorMapper::toResponseDTO);
    }

    @Override
    public void addWorkingHour(Long doctorId, WorkingHourRequestDTO workingHourDTO) {
        var doctor = this.getDoctorById(doctorId);
        if(!workingHourRepository.findOverlappingWorkingHours(doctorId,workingHourDTO.getDayOfWeek(),
                workingHourDTO.getTimeFrom(), workingHourDTO.getTimeTo()).isEmpty()){
            throw new BadRequestException("The working hour overlaps with an existing working hour");
        }
        doctor.addWorkingHour(workingHourMapper.toEntity(workingHourDTO));
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
    public List<DoctorResponseDTO> findAvailableDoctors(LocalDateTime start, LocalDateTime end, Long serviceId) {
        var doctors = doctorRepository.findAvailableDoctors(start.getDayOfWeek(),start.toLocalTime(),
                serviceId, start, end);
        return doctors.stream().map(doctorMapper::toResponseDTO).toList();
    }

    @Override
    public void addService(Long doctorId, Long serviceId) {
        isAllowedToModify(doctorId);
        var doctor = this.getDoctorById(doctorId);
        var service = this.getServiceById(serviceId);
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
    public boolean ifDoctorProvidesService(Long serviceId, Long doctorId) {
        System.out.println("Verifying doctorId: " + doctorId + " with serviceId: " + serviceId);
        return doctorRepository.existsByIdAndServicesId(doctorId, serviceId);
    }

    @Override
    public boolean ifDoctorWorksThisDayAtTime(Long doctorId, LocalDateTime timeFrom, LocalDateTime timeTo) {
        return doctorRepository.ifDoctorWorksThisDayAtTime(
                doctorId,
                timeFrom.getDayOfWeek(),
                timeFrom.toLocalTime(),
                timeTo.toLocalTime()
        );
    }


    @Override
    public DoctorResponseDTO findById(Long id) {
        var doctor = this.getDoctorById(id);
        return doctorMapper.toResponseDTO(doctor);
    }


    @Override
    public DoctorResponseDTO update(DoctorRequestDTO doctorRequestDTO) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    public Doctor getDoctorById(Long id) {
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
