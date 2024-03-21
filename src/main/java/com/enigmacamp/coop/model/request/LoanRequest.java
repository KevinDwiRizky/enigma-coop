package com.enigmacamp.coop.model.request;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private Long amount;
    private Double interestRate; //bunga
    private Date startDate; // tanggal mulai
    private Date dueDate; // jatuh tempo
    private LoanStatusEnum status; // status
    private String nasabahId; //nasabah id
}
