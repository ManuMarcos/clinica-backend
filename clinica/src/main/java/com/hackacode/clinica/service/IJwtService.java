package com.hackacode.clinica.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.function.Function;

public interface IJwtService {
    String generateToken(UserDetails usuario, Long userId);
    String generateRefreshToken(UserDetails usuario, Long userId);
    String getUsernameFromToken(String token);
    Long getUserIdFromToken(String token);
    List<String> getRolesFromToken(String token);
    <T> T getClaim(String token, Function<Claims, T> claimsResolver );
    boolean isTokenValid(String token, UserDetails userDetails);
    String getTokenFromCurrentRequest();

}
