package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadByUserId(String userId);
}
