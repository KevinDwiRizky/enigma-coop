package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.LoanRequest;
import com.enigmacamp.coop.model.request.SearchLoanRequest;
import com.enigmacamp.coop.model.response.LoanResponse;
import com.enigmacamp.coop.repository.LoanRepository;
import com.enigmacamp.coop.service.LoanService;
import com.enigmacamp.coop.service.NasabahService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final NasabahService nasabahService;
    private final LoanRepository loanRepository;
    @Override
    @Transactional
    public LoanResponse createLoan(LoanRequest loanRequest) {
        Nasabah nasabah = nasabahService.getNasabahById(loanRequest.getNasabahId());

        Calendar calendar = getCalendar();


        Loan newLoan = Loan.builder()
                .amount(loanRequest.getAmount())
                .interestRate(0.05)
                .dueDate(calendar.getTime())
                .status(LoanStatusEnum.PENDING)
                .nasabah(nasabah)
                .build();

        // Menghitung bunga
        Double interest = newLoan.getAmount() * 0.05; // Bunga 5%
        Double totalPayment = newLoan.getAmount() + interest;
        Loan saveLoan = loanRepository.saveAndFlush(newLoan);
        return LoanResponse.builder()
                .id(saveLoan.getId())
                .amount(saveLoan.getAmount())
                .interestRate(saveLoan.getInterestRate())
                .dueDate(saveLoan.getDueDate())
                .startDate(saveLoan.getStartDate())
                .nasabah(saveLoan.getNasabah())
                .status(saveLoan.getStatus())
                .totalPayment(totalPayment.longValue())
                .build();
    }

    private static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        int daysNextMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);

        if (daysNextMonth <= 15) {
            calendar.add(Calendar.MONTH, 2);
        } else {
            calendar.add(Calendar.MONTH, 1);
        }

        calendar.set(Calendar.DAY_OF_MONTH, 5);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @Override
    public List<Loan> getLoanByNasabahId(String id) {
        return loanRepository.findByNasabahId(id);
    }

    private Specification<Loan> amountBetweenSpesificaion(Long minAmount, Long maxAmount) {
        return (root, query, criteriaBuilder) -> {
            Predicate minAmountPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount);
            Predicate maxAmountPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount);
            return criteriaBuilder.and(minAmountPredicate, maxAmountPredicate);
        };
    }


    @Override
    public Page<Loan> getAllFilterLoan(SearchLoanRequest searchLoanRequest) {

        if (searchLoanRequest.getPage() <= 0){
            searchLoanRequest.setPage(1);
        }
        Specification<Loan> loanSpecification = amountBetweenSpesificaion(
                searchLoanRequest.getMinAmount(),
                searchLoanRequest.getMaxAmount()
        );

        Pageable pageable = PageRequest.of(searchLoanRequest.getPage()-1, searchLoanRequest.getSize());
        return loanRepository.findAll(loanSpecification,pageable);

    }


    @Override
    public List<Loan> findLoan(Long amount, Double interestRate, Date startDate, Date dueDate, LoanStatusEnum status, String nasabahId) {
        // Buat spesifikasi (Specification) untuk kueri dinamis
        Specification<Loan> spec = (root, query, criteriaBuilder) -> {
            // Inisialisasi list of predicates
            List<Predicate> predicates = new ArrayList<>();

            // Tambahkan predikat jika nilai parameter tidak null atau kosong
            if (amount != null) {
                predicates.add(criteriaBuilder.equal(root.get("amount"), amount));
            }
            if (interestRate != null) {
                predicates.add(criteriaBuilder.equal(root.get("interestRate"), interestRate));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("startDate"), startDate));
            }
            if (dueDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("dueDate"), dueDate));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (nasabahId != null) {
                // Dapatkan Nasabah berdasarkan ID
                Nasabah nasabah = nasabahService.getNasabahById(nasabahId);
                predicates.add(criteriaBuilder.equal(root.get("nasabah"), nasabah));
            }

            // Gabungkan semua predikat dengan operator AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Dapatkan daftar Loan berdasarkan spesifikasi
        return loanRepository.findAll(spec);
    }

}

/*

root: Merepresentasikan entitas yang sedang Anda query, memberikan akses ke atribut-atribut entitas tersebut.
query: Digunakan untuk membangun kueri JPA, seperti menambahkan klausa WHERE, ORDER BY, dan lain-lain.
criteriaBuilder: Digunakan untuk membuat berbagai kriteria query, seperti persamaan, kecocokan parsial, dan lain-lain.
predicate: Mewakili kondisi dalam kueri JPA, yang digunakan untuk memfilter hasil query berdasarkan kriteria tertentu.

 */
