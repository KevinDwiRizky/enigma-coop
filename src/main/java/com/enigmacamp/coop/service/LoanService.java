package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.LoanRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {
    Loan createLoan(LoanRequest loanRequest);
    Page<Loan> getAllLoan(Integer page, Integer size);

    Loan getLoanById(String id);

}
