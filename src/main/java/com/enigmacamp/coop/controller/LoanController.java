package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.LoanRequest;
import com.enigmacamp.coop.model.response.PagingResponse;
import com.enigmacamp.coop.model.response.WebResponse;
import com.enigmacamp.coop.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody LoanRequest loanRequest){
        Loan newLoan = loanService.createLoan(loanRequest);
        WebResponse<Loan> response = WebResponse.<Loan>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success create Loan")
                .data(newLoan)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getLoanByNasabahId(@PathVariable String id){
        List<Loan> loanList = loanService.getLoanByNasabahId(id);
        WebResponse<List<Loan>> response= WebResponse.<List<Loan>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get loans by Nasabah Id")
                .data(loanList)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Loan>> findLoan(
            @RequestParam(required = false) Long amount,
            @RequestParam(required = false) Double interestRate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dueDate,
            @RequestParam(required = false) LoanStatusEnum status,
            @RequestParam(required = false) String nasabahId
    ) {
        List<Loan> loans = loanService.findLoan(amount, interestRate, startDate, dueDate, status, nasabahId);
        return ResponseEntity.ok(loans);
    }
}
