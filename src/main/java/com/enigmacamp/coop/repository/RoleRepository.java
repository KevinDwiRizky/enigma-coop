package com.enigmacamp.coop.repository;

import com.enigmacamp.coop.constant.RoleEnum;
import com.enigmacamp.coop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String > {

    Optional<Role> findByRole(RoleEnum role);
}
