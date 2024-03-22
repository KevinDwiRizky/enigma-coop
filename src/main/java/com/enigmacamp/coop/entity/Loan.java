package com.enigmacamp.coop.entity;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "m_loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long amount;
    private Double interestRate;
    private Date startDate;
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    private LoanStatusEnum status;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "nasabah_id")
    private Nasabah nasabah;
}
