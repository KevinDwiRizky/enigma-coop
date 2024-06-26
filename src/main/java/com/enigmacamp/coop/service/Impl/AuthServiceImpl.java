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
import com.enigmacamp.coop.repository.NasabahRepository;
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
        // Untuk role
        Role roleCustomer = roleService.getOrSave(RoleEnum.ROLE_ADMIN);
        // Hash password
        String hashPassword = passwordEncoder.encode(nasabahRequest.getPassword());

        // Buat dan simpan UserCredential terlebih dahulu
        UserCredential userCredential = UserCredential.builder()
                .username(nasabahRequest.getUsername())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();
        credentialRepository.saveAndFlush(userCredential);

        Nasabah nasabah = nasabahService.createNasabah(nasabahRequest, userCredential);

        List<String> roles = userCredential.getRoles().stream()
                .map(role -> role.getRole().name())
                .toList();

        return NasabahResponse.builder()
                .fullname(nasabah.getFullname())
                .email(nasabah.getEmail())
                .phoneNumber(nasabah.getPhoneNumber())
                .address(nasabah.getAddress())
                .username(userCredential.getUsername())
                .role(roles)
                .userCredential(userCredential.getId()) // Menambahkan ID UserCredential ke dalam respons
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
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        JwtClaim jwtClaim = jwtUtils.getUserInfoByToken(token);
        if (jwtClaim != null) {
            String userId = jwtClaim.getUserId();
            // Panggil layanan untuk mendapatkan data nasabah berdasarkan userId
            List<Nasabah> nasabahList = nasabahService.getNasabahByUserId(userId);
            // Lakukan apa pun yang perlu dilakukan dengan data nasabah
            // Misalnya, kembalikan data nasabah atau lakukan operasi lain
            // ...
            // Contoh: Mengembalikan daftar nasabah dalam bentuk JSON
            return nasabahList.toString();
        } else {
            return null;
        }
    }



}
