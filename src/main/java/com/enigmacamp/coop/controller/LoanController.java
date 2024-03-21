package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.LoanRequest;
import com.enigmacamp.coop.model.response.PagingResponse;
import com.enigmacamp.coop.model.response.WebResponse;
import com.enigmacamp.coop.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping
//    public ResponseEntity<?> getAllLoan(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer size
//    ){
//        Page<Loan> loanList = loanService.getAllLoan(page, size);
//
//        PagingResponse pagingResponse = PagingResponse.builder()
//                .page(page).size(size)
//                .totalPages(loanList.getTotalPages())
//                .totalElement(loanList.getTotalElements())
//                .build();
//
//        WebResponse<List<Loan>> response = WebResponse.<List<Loan>>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Get List Loan")
//                .paging(pagingResponse)
//                .data(loanList.getContent())
//                .build();
//        return ResponseEntity.ok(response);
//    }


//    @GetMapping(path = "/{id}")
//    public ResponseEntity<?> getLoanById(@PathVariable String id) {
//        Loan findLoan = loanService.getLoanById(id);
//        WebResponse<Loan> response = WebResponse.<Loan>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success Get By Id ")
//                .data(findLoan)
//                .build();
//        return ResponseEntity.ok(response);
//    }
}
