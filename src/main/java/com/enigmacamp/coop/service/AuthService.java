package com.enigmacamp.coop.service;

import com.enigmacamp.coop.model.request.NasabahRequest;
import com.enigmacamp.coop.model.response.NasabahResponse;

public interface AuthService {
    NasabahResponse register(NasabahRequest nasabahRequest);
}
