package com.enigmacamp.coop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_saving")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nasabah_id", referencedColumnName = "id")
    private Nasabah nasabah;
}
