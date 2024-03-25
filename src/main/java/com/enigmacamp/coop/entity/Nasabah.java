package com.enigmacamp.coop.entity;

import com.enigmacamp.coop.constant.NasabahStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    private String address;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Jakarta")
    private Date joinDate;
    @Enumerated(EnumType.STRING)
    private NasabahStatus status;

    @PrePersist
    protected  void onCreate(){
        joinDate = new Date();
    }

}
