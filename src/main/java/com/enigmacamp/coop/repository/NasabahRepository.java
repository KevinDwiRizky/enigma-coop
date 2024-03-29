package com.enigmacamp.coop.repository;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NasabahRepository extends JpaRepository<Nasabah, String > {
    Nasabah findByEmail(String email);
    List<Nasabah> findByUserCredentialId(String userCredentialId);
}
