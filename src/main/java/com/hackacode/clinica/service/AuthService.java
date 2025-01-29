package com.hackacode.clinica.service;

import com.hackacode.clinica.controller.DoctorController;
import com.hackacode.clinica.dto.LoginRequestDTO;
import com.hackacode.clinica.dto.RegisterRequestDTO;
import com.hackacode.clinica.dto.TokenResponseDTO;
import com.hackacode.clinica.dto.UserResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.model.*;
import com.hackacode.clinica.repository.IDoctorRepository;
import com.hackacode.clinica.repository.IPatientRepository;
import com.hackacode.clinica.repository.ISpecialityRepository;
import com.hackacode.clinica.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final IPatientRepository patientRepository;
    private final ISpecialityRepository specialityRepository;
    private final IDoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Transactional
    public TokenResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        this.validateUniqueConstraints(registerRequestDTO);
        if(registerRequestDTO.getRole() == Role.DOCTOR) {
            var newDoctor = validateAndCreateDoctorFromDto(registerRequestDTO);
            var savedDoctor = doctorRepository.save(newDoctor);
            return new TokenResponseDTO(jwtService.generateToken(savedDoctor));
        }
        else if(registerRequestDTO.getRole() == Role.PATIENT) {
            var newPatient = validateAndCreatePatientFromDto(registerRequestDTO);
            var savedPatient = patientRepository.save(newPatient);
            return new TokenResponseDTO(jwtService.generateToken(savedPatient));
        }
        else { //Role = ADMIN
            var newUser = createUser(registerRequestDTO);
            var savedUser = userRepository.save(newUser);
            return new TokenResponseDTO(jwtService.generateToken(savedUser));
        }
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        UserDetails user = userRepository.findByEmail(loginRequestDTO.email()).orElse(null);
        String token = jwtService.generateToken(user);
        return new TokenResponseDTO(token);
    }


    private UserResponseDTO userToDTO(User user) {
        return null;
    }

    private Doctor validateAndCreateDoctorFromDto(RegisterRequestDTO request) {
        if (request.getSpecialityId() == null) {
            throw new IllegalArgumentException("Doctors must have speciality");
        }

        if (request.getSalary() == null) {
            throw new IllegalArgumentException("Doctors must have salary");
        }
        Speciality speciality = specialityRepository.findById(request.getSpecialityId())
                .orElseThrow(() -> new ResourceNotFoundException("Speciality not found"));

        Doctor doctor = new Doctor();
        doctor.setEmail(request.getEmail());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setDni(String.valueOf(request.getDni()));
        doctor.setName(request.getName());
        doctor.setSurname(request.getSurname());
        doctor.setSalary(request.getSalary());
        doctor.setRole(request.getRole());
        doctor.setSpeciality(speciality);
        return doctor;
    }

    private Patient validateAndCreatePatientFromDto(RegisterRequestDTO request) {
        if(request.getHealthInsurance() == null) {
            throw new IllegalArgumentException("Patient must have Health Insurance");
        }

        HealthInsurance healthInsurance = HealthInsurance.builder()
                .plan(request.getHealthInsurance().getPlan())
                .name(request.getHealthInsurance().getName())
                .number(request.getHealthInsurance().getNumber())
                .build();
        Patient patient = new Patient();
        patient.setEmail(request.getEmail());
        patient.setDni(request.getDni());
        patient.setName(request.getName());
        patient.setSurname(request.getSurname());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setRole(request.getRole());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setHealthInsurance(healthInsurance);
        return patient;
    }

    private User createUser(RegisterRequestDTO request) {
        return User.builder()
                .dni(request.getDni())
                .email(request.getEmail())
                .name(request.getName())
                .surname(request.getSurname())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .role(request.getRole())
                .build();
    }

    private void validateUniqueConstraints(RegisterRequestDTO request){
        if(userService.existsByDni(request.getDni())){
            throw new IllegalArgumentException("Dni is already in use");
        }
        if(userService.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email is already in use");
        }
    }




}
