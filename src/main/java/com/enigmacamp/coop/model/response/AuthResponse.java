package com.enigmacamp.coop.model.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String username;
    private List<String> roles;
}
