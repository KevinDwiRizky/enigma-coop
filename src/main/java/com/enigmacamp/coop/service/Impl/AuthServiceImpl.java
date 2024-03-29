package com.enigmacamp.coop.service.Impl;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.coop.constant.RoleEnum;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.entity.Role;
import com.enigmacamp.coop.entity.UserCredential;
import com.enigmacamp.coop.model.JwtClaim;
import com.enigmacamp.coop.model.request.AuthRequest;
import com.enigmacamp.coop.model.request.NasabahRequest;
import com.enigmacamp.coop.model.response.AuthResponse;
import com.enigmacamp.coop.model.response.NasabahResponse;
import com.enigmacamp.coop.repository.UserCredentialRepository;
import com.enigmacamp.coop.security.JwtUtils;
import com.enigmacamp.coop.service.AuthService;
import com.enigmacamp.coop.service.NasabahService;
import com.enigmacamp.coop.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleService roleService;
    private final UserCredentialRepository credentialRepository;
    private final NasabahService nasabahService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Value("${app.enigma-coop.username-admin}")
    private String usernameAdmin;

    @Value("${app.enigma-coop.password-admin}")
    private String passwordAdmin; // Mengubah nama variabel menjadi passwordAdmin

    public void initSuperAdmin(){
        Optional<UserCredential> optionalUserCredential = credentialRepository.findByUsername(usernameAdmin);
        if(optionalUserCredential.isPresent()) return;

        Role superAdminRole = roleService.getOrSave(RoleEnum.ROLE_SUPER_ADMIN);
        Role adminRole = roleService.getOrSave(RoleEnum.ROLE_ADMIN);
        Role customerRole = roleService.getOrSave(RoleEnum.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(passwordAdmin); // Menggunakan passwordAdmin

        UserCredential userCredential = UserCredential.builder()
                .username(usernameAdmin)
                .password(hashPassword)
                .roles(List.of(superAdminRole, adminRole, customerRole))
                .build();
        credentialRepository.saveAndFlush(userCredential);
    }


    @Override
    @Transactional
    public NasabahResponse register(NasabahRequest nasabahRequest) {
        //untuk role
        Role roleCustomer = roleService.getOrSave(RoleEnum.ROLE_CUSTOMER);
        //hash password
        String hashPassword = passwordEncoder.encode(nasabahRequest.getPassword());
        //new nasabah
        Nasabah nasabah = nasabahService.createNasabah(nasabahRequest);
        //user credential baru
        UserCredential userCredential = UserCredential.builder()
                .username(nasabahRequest.getUsername())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();
        UserCredential savedUserCredential = credentialRepository.saveAndFlush(userCredential);
        //List role
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return NasabahResponse.builder()
                .fullname(nasabah.getFullname())
                .email(nasabah.getEmail())
                .phoneNumber(nasabah.getPhoneNumber())
                .address(nasabah.getAddress())
                .username(savedUserCredential.getUsername())
                .role(roles)
                .build();
    }

    @Override
    public String login(AuthRequest authRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        //call method untuk kebutuhan validasi credentilnya
        Authentication authenticate = authenticationManager.authenticate(authentication);

        // jika valid maka selanjutnya simpan sesi
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // berikan token
        UserCredential userCredential = (UserCredential) authenticate.getPrincipal();
        return jwtUtils.generateToken(userCredential);
    }

    @Override
    public String getUserIdFromToken(String token) {
        JwtClaim jwtClaim = jwtUtils.getUserInfoByToken(token);
        if (jwtClaim != null) {
            return jwtClaim.getUserId();
        } else {
            return null;
        }
    }

}
