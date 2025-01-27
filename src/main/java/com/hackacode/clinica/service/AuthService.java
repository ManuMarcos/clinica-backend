package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.LoginRequestDTO;
import com.hackacode.clinica.dto.RegisterRequestDTO;
import com.hackacode.clinica.dto.TokenResponseDTO;
import com.hackacode.clinica.model.User;
import com.hackacode.clinica.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository IUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        var user = User.builder()
                .dni(registerRequestDTO.getDni())
                .name(registerRequestDTO.getName())
                .surname(registerRequestDTO.getSurname())
                .email(registerRequestDTO.getEmail())
                .dateOfBirth(registerRequestDTO.getDateOfBirth())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(registerRequestDTO.getRole())
                .build();
        var savedUser = IUserRepository.save(user);
        return new TokenResponseDTO(jwtService.generateToken(savedUser));
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        UserDetails user = IUserRepository.findByEmail(loginRequestDTO.email()).orElse(null);
        String token = jwtService.generateToken(user);
        return new TokenResponseDTO(token);
    }

    private void validateMedico(RegisterRequestDTO request) {

    }


}
