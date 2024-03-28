package com.enigmacamp.coop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "nasabah_id", referencedColumnName = "id")
    private Nasabah nasabah;

    @OneToMany(mappedBy = "saving", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TrxSaving> trxSavingList;
}
