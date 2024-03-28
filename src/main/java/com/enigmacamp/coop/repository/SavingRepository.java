package com.enigmacamp.coop.repository;

import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<Saving, String> {
    Saving findByNasabahId(String nasabahId);

}
