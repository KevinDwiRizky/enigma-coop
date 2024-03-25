package com.enigmacamp.coop.entity;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Enumerated(EnumType.STRING)
    private LoanStatusEnum status;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "nasabah_id")
    private Nasabah nasabah;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Jakarta")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Jakarta")
    private Date dueDate;

    @PrePersist
    protected  void onCreate(){
        startDate = new Date();
    }

}
