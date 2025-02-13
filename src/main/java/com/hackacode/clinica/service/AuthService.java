package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.AccessTokenDTO;
import com.hackacode.clinica.dto.LoginRequestDTO;
import com.hackacode.clinica.dto.LoginResponseDTO;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.UserMapper;
import com.hackacode.clinica.model.*;
import com.hackacode.clinica.repository.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
public class AuthService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IRefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        User user = userRepository.findByEmail(loginRequestDTO.email()).orElseThrow(
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
                .user(userMapper.toDTO(user))
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
                .build();
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
