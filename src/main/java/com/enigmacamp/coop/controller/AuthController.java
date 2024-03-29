package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.AuthRequest;
import com.enigmacamp.coop.model.request.NasabahRequest;
import com.enigmacamp.coop.model.response.NasabahResponse;
import com.enigmacamp.coop.model.response.WebResponse;
import com.enigmacamp.coop.service.AuthService;
import com.enigmacamp.coop.service.NasabahService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final NasabahService nasabahService;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<NasabahResponse>> createNasabah(
            @Valid @RequestBody NasabahRequest nasabahRequest){
        NasabahResponse nasabahResponse = authService.register(nasabahRequest);
        WebResponse<NasabahResponse> response = WebResponse.<NasabahResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succes register new nasabah")
                .data(nasabahResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest); // Mengganti "register" menjadi "login" sesuai dengan konteks
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase()) // Mengubah status ke OK
                .message("Login successful") // Pesan berhasil login
                .data(token) // Mengirim token JWT sebagai data
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String userId = authService.getUserIdFromToken(token);
        if (userId != null) {
            WebResponse<String> response = WebResponse.<String>builder()
                    .status(HttpStatus.OK.getReasonPhrase())
                    .message("Success Get By Id ")
                    .data(userId)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unable to get user ID from token");
        }
    }

//    @GetMapping("/profile")
//    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
//    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
//        String userId = authService.getUserIdFromToken(token);
//        if (userId != null) {
//            List<Nasabah> nasabahList = nasabahService.getNasabahByUserId(userId);
//            if (!nasabahList.isEmpty()) {
//                WebResponse<List<Nasabah>> response = WebResponse.<List<Nasabah>>builder()
//                        .status(HttpStatus.OK.getReasonPhrase())
//                        .message("Success Get By Id")
//                        .data(nasabahList)
//                        .build();
//                return ResponseEntity.ok(response);
//            } else {
//                return ResponseEntity.notFound().build(); // Tidak ada Nasabah ditemukan
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unable to get user ID from token");
//        }
//    }



}
