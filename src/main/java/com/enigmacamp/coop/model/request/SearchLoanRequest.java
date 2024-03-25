package com.enigmacamp.coop.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchLoanRequest {
    private Integer page;
    private Integer size;
    private Long minAmount;
    private Long maxAmount;
}
