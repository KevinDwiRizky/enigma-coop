package com.enigmacamp.coop.model.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NasabahResponse {
    private String fullname;
    private String phoneNumber;
    private String email;
    private String address;

    private String username;
    private List<String> role;
}
