package com.enigmacamp.coop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.enigmacamp.coop.entity.UserCredential;

import java.time.Instant;
import java.util.List;

import com.enigmacamp.coop.model.JwtClaim;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {
    @Value("${app.enigma-coop.jwt-app-name}")
    private String appName;

    @Value("${app.enigma-coop.jwt-expiration}")
    private Long expirationInSecond;
    @Value("${app.enigma-coop.jwt-secret}")
    private String secretKey;

    // Method untuk menghasilkan JWT (JSON Web Token) berdasarkan kredensial pengguna
    public String generateToken(UserCredential userCredential) {
        try {
            // Mendapatkan peran dari kredensial pengguna dan memetakkannya ke nama peran
            List<String> roles = userCredential.getRoles()
                    .stream()
                    .map(role -> role.getRole().name()).toList();

            // Membuat JWT dengan penerbit, subjek, waktu kedaluwarsa, dan klaim peran
            return JWT
                    .create()
                    .withIssuer(appName)
                    .withSubject(userCredential.getId())
                    .withExpiresAt(Instant.now().plusSeconds(expirationInSecond))
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC512(secretKey)); // Menandatangani JWT dengan algoritma HMAC512 dan kunci rahasia
        } catch (JWTCreationException e) {
            // Menangani pengecualian selama pembuatan JWT
            log.error("Error while creating JWT token: {}", e.getMessage());
            throw new RuntimeException("Error while creating JWT token", e);
        }
    }

    // Method untuk memverifikasi keaslian token JWT
    public boolean verifyJwtToken(String token){
        try{
            // Mendekode dan memverifikasi token JWT
            DecodedJWT decodedJWT = getDecodedJWT(token);

            // Memeriksa apakah penerbit token cocok dengan nama aplikasi
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException e){
            // Menangani pengecualian selama verifikasi JWT
            log.error("Error verifying JWT token: {}", e.getMessage());
            return false;
        }
    }

    // Metode utilitas untuk mendekode dan memverifikasi token JWT
    private DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }

    // Method untuk mengambil informasi pengguna dari token JWT
    public JwtClaim getUserInfoByToken(String token){
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

            return JwtClaim.builder()
                    .userId(decodedJWT.getSubject())
                    .roles(roles)
                    .build();
        } catch (JWTVerificationException e){
            log.error("Invalid Verification info user jwt : {} ", e.getMessage());
            return null;
        }
    }
}
