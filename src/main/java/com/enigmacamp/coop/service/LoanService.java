package com.enigmacamp.coop.service;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.LoanRequest;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface LoanService {
    Loan createLoan(LoanRequest loanRequest);
    List<Loan> getLoanByNasabahId(String id);
    List<Loan> findLoan(Long amount,Double interestRate, Date startDate, Date dueDate, LoanStatusEnum status, String nasabahId);
}
