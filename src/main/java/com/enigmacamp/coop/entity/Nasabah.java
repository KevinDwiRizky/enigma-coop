package com.enigmacamp.coop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "m_nasabah")
public class Nasabah {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullname;
    private String phoneNumber;
    private String address;
    private Date joinDate;
    private String status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "saving_id")
    private Saving saving;

}
