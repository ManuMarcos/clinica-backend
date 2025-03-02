package com.hackacode.clinica.service.impl;

import com.hackacode.clinica.dto.authentication.AccessTokenDTO;
import com.hackacode.clinica.dto.authentication.LoginRequestDTO;
import com.hackacode.clinica.dto.authentication.LoginResponseDTO;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.IUserMapper;
import com.hackacode.clinica.model.*;
import com.hackacode.clinica.repository.*;
import com.hackacode.clinica.service.IAuthService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final IRefreshTokenRepository refreshTokenRepository;
    private final IUserMapper userMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        refreshTokenRepository.revokeToken(user.getId());
        String accessToken = jwtService.generateToken(user, user.getId());
        String refreshToken = jwtService.generateRefreshToken(user, user.getId());
        refreshTokenRepository.save(
                RefreshToken.builder()
                .token(refreshToken)
                .creationDate(LocalDateTime.now())
                .revoked(false)
                .user(user)
                .build());

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toResponseDTO(user))
                .build();
    }

    public AccessTokenDTO refreshToken(@NotNull final String authentication) {
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authentication header");
        }
        final String refreshToken = authentication.substring("Bearer ".length());
        var refreshTokenEntity = refreshTokenRepository.findByTokenAndRevokedFalse(refreshToken).orElseThrow(
                () -> new ResourceNotFoundException("Refresh token not found or revoked")
        );
        var user = refreshTokenEntity.getUser();
        if(!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String newAccessToken = jwtService.generateToken(user, user.getId());
        return AccessTokenDTO.builder()
                .accessToken(newAccessToken)
                .user(userMapper.toResponseDTO(user))
                .build();
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

}
