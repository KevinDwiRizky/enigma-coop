package com.enigmacamp.coop.repository;

import com.enigmacamp.coop.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {

    List<Loan> findByNasabahId(String nasabahId);

}
