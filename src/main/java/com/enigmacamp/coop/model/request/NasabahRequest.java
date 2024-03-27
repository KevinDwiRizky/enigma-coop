package com.enigmacamp.coop.model.request;

import com.enigmacamp.coop.constant.NasabahStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NasabahRequest {
    @NotBlank(message = "Fullname is mandatory and cannot be blank")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Fullname must contain only alphabets")
    private String fullname;

    @NotBlank(message = "Phone number is mandatory and cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    @Size(min = 10, max = 15, message = "Phone number length must be between 10 and 15 digits")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory and cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Address is mandatory and cannot be blank")
    private String address;

    private String username;
    private String password;
}
