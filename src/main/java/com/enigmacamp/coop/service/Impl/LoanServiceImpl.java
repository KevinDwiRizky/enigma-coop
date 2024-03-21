package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.LoanRequest;
import com.enigmacamp.coop.repository.LoanRepository;
import com.enigmacamp.coop.service.LoanService;
import com.enigmacamp.coop.service.NasabahService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final NasabahService nasabahService;
    private final LoanRepository loanRepository;
    @Override
    @Transactional
    public Loan createLoan(LoanRequest loanRequest) {

        Nasabah nasabah = nasabahService.getNasabahById(loanRequest.getNasabahId());
        Loan newLoan = Loan.builder()
                .amount(loanRequest.getAmount())
                .interestRate(loanRequest.getInterestRate())
                .startDate(loanRequest.getStartDate())
                .dueDate(loanRequest.getDueDate())
                .status(loanRequest.getStatus())
                .nasabah(nasabah)
                .build();
        loanRepository.saveAndFlush(newLoan);
        return newLoan;
    }

    @Override
    public Page<Loan> getAllLoan(Integer page, Integer size) {
        if (page <=0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return loanRepository.findAll(pageable);
    }

    @Override
    public Loan getLoanById(String id) {
        Optional<Loan> optionalLoan=loanRepository.findById(id);
        if (optionalLoan.isPresent()) return optionalLoan.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id : " + id + " Not Found");

    }

}
