package com.enigmacamp.coop.entity;

import com.enigmacamp.coop.constant.SavingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    private Long amount;
    @ManyToOne
    @JoinColumn(name = "saving_id")
    private Saving saving;

//    @ManyToOne
//    @JoinColumn(name = "saving_type_id")
//    private SavingType savingType;

    @Enumerated(EnumType.STRING)
    private SavingType savingType;

    private Date date;


}
