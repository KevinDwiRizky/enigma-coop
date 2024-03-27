package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.constant.RoleEnum;
import com.enigmacamp.coop.entity.Role;
import com.enigmacamp.coop.repository.RoleRepository;
import com.enigmacamp.coop.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(RoleEnum role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        if (optionalRole.isPresent()) return optionalRole.get();

        return roleRepository.saveAndFlush(
                Role.builder()
                        .role(role).build()
        );
    }
}
