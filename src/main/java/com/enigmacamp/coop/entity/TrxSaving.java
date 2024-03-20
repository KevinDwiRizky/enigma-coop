package com.enigmacamp.coop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_trx_saving")
public class TrxSaving {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
