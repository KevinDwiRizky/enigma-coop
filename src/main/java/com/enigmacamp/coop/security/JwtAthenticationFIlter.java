package com.enigmacamp.coop.security;

import com.enigmacamp.coop.model.JwtClaim;
import com.enigmacamp.coop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
@Component
public class JwtAthenticationFIlter extends OncePerRequestFilter {

    // Dependency JwtUtils dan UserService
    private final JwtUtils jwtUtils;
    private final UserService userService;

    // Metode untuk melakukan logika filter pada setiap permintaan masuk
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            // Mendapatkan token JWT dari permintaan
            String token = parseJwt(request);
            // Memeriksa apakah token ada dan valid
            if (token != null && jwtUtils.verifyJwtToken(token)){
                // Mendapatkan informasi pengguna dari token JWT
                JwtClaim userInfo = jwtUtils.getUserInfoByToken(token);
                // Mendapatkan detail pengguna dari database
                UserDetails userDetails = userService.loadByUserId(userInfo.getUserId());
                // Membuat token otentikasi
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // Menetapkan token otentikasi di konteks keamanan
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            // Menangkap pengecualian yang terjadi dan mencatat pesan kesalahan
            log.error("Tidak dapat mengatur Otentikasi pengguna: {} ", e.getMessage());
        }

        // Memanggil filter berikutnya dalam rantai filter
        filterChain.doFilter(request,response);
    }

    // Metode untuk menguraikan token JWT dari header otorisasi permintaan
    private String parseJwt(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
