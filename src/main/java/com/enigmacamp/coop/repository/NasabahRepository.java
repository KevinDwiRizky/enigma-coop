package com.enigmacamp.coop.repository;

import com.enigmacamp.coop.model.Nasabah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NasabahRepository extends JpaRepository<Nasabah, String > {
}
