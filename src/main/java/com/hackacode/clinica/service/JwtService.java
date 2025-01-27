package com.hackacode.clinica.service;

import com.hackacode.clinica.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mySecretKey432429842394892848929834832489829843293983429832";
    private static final Long JWT_EXPIRATION = 86400000L; //24 horas
    private static final Long REFRESH_EXPIRATION = 604800000L;

    public String generateToken(UserDetails usuario){
        return this.buildToken(new HashMap<>(), usuario, JWT_EXPIRATION);
    }

    public String generateRefreshToken(User user){
        return "";
    }


    private String buildToken(Map<String, Object> extraClaims, UserDetails usuario, Long expiracion){
        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // Extrae los roles
                .toList();
        extraClaims.put("roles", roles);

        return Jwts.builder()
                .claims(extraClaims)
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

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("roles", List.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver ) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
