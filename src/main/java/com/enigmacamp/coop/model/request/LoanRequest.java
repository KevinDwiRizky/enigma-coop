package com.enigmacamp.coop.model.request;

import com.enigmacamp.coop.constant.LoanStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private Long amount;
    private String nasabahId; //nasabah id
}
