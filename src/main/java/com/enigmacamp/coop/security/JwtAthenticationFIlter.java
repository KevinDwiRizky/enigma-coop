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

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = parseJwt(request);
            if (token != null && jwtUtils.verifyJwtToken(token)){
                JwtClaim userInfo = jwtUtils.getUserInfoByToken(token);
                UserDetails userDetails = userService.loadByUserId(userInfo.getUserId());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }catch (Exception e){

            log.error("Cannont ser user Authentication : {} ", e.getMessage());

        }

        filterChain.doFilter(request,response);

    }

    private String parseJwt(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }


}
