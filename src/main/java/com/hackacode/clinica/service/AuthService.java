package com.hackacode.clinica.service;

import com.hackacode.clinica.config.JwtAuthenticationFilter;
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
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        User user = userRepository.findByEmail(loginRequestDTO.email()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        String token = jwtService.generateToken(user, user.getId());
        return new TokenResponseDTO(token);
    }

    public boolean isPatient(){
        return hasRole("PATIENT");
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    private boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_" + role));
        }
        return false;
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public Long getCurrentUserId() {
        return jwtService.getUserIdFromToken(jwtService.getTokenFromCurrentRequest());
    }

    public List<Role> getCurrentUserRoles() {
        return jwtService.getRolesFromToken(jwtService.getTokenFromCurrentRequest()).stream().map(
                Role::valueOf
        ).toList();
    }



}
