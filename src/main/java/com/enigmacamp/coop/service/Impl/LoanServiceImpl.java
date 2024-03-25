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
    public Loan createLoan(LoanRequest loanRequest) {

        Nasabah nasabah = nasabahService.getNasabahById(loanRequest.getNasabahId());

        // 1 bulan dari request pinjaman
        Calendar calendar =Calendar.getInstance();
        int daysNextMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);

        // due date / jatuh tempo selalu akan pada tanggal 5 setelah hitungan satu bulan
        // jika  jarak ke tanggal 5 hanya 15 atau kurang, maka akan ditambah menuju bulan depannya
        if (daysNextMonth <= 15){
            calendar.add(Calendar.MONTH, 2);
        } else {
            calendar.add(Calendar.MONTH, 1);
        }

        calendar.set(Calendar.DAY_OF_MONTH, 5);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Loan newLoan = Loan.builder()
                .amount(loanRequest.getAmount())
                .interestRate(5.0)
                .dueDate(calendar.getTime())
                .status(LoanStatusEnum.PENDING)
                .nasabah(nasabah)
                .build();
        loanRepository.saveAndFlush(newLoan);
        return newLoan;
    }

    @Override
    public List<Loan> getLoanByNasabahId(String id) {
        return loanRepository.findByNasabahId(id);
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
