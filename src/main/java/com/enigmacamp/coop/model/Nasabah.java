package com.enigmacamp.coop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
}
