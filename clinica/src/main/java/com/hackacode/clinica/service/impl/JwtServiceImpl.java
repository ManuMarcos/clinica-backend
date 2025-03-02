package com.hackacode.clinica.service.impl;

import com.hackacode.clinica.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${JWT_SECRET}")
    private String SECRET_KEY; 
    private static final Long JWT_EXPIRATION =  86400000L; //24 hours
    private static final Long REFRESH_TOKEN_EXPIRATION = 2592000000L; //30 days

    @PostConstruct
    public void logEnvVariables() {
        System.out.println("SECRET_KEY en JwtService: " + this.SECRET_KEY);
    }

    public String generateToken(UserDetails usuario, Long userId){
        return this.buildToken(new HashMap<>(), usuario, JWT_EXPIRATION, userId);
    }

    public String generateRefreshToken(UserDetails usuario, Long userId){
        return this.buildToken(new HashMap<>(), usuario, REFRESH_TOKEN_EXPIRATION, userId);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("roles", List.class);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver ) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getTokenFromCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        return extractToken(request);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails usuario, Long expiracion, Long userId){
        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // Extrae los roles
                .toList();
        extraClaims.put("roles", roles);
        extraClaims.put("userId", userId);
        return Jwts.builder()
                .claims(extraClaims)
                .id(UUID.randomUUID().toString())
                .subject(usuario.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiracion))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
