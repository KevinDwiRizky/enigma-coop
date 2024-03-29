package com.enigmacamp.coop.service;

import com.enigmacamp.coop.model.request.AuthRequest;
import com.enigmacamp.coop.model.request.NasabahRequest;
import com.enigmacamp.coop.model.response.AuthResponse;
import com.enigmacamp.coop.model.response.NasabahResponse;

public interface AuthService {
    NasabahResponse register(NasabahRequest nasabahRequest);
    String login(AuthRequest authRequest);
    String getUserIdFromToken(String token);
}
