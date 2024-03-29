package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.entity.UserCredential;
import com.enigmacamp.coop.model.request.NasabahRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NasabahService {
    Nasabah createNasabah(NasabahRequest nasabahRequest, UserCredential userCredential);

    Page<Nasabah> getAllNasabah(Integer page, Integer size);

    Nasabah getNasabahById(String id);
    Nasabah getNasabahByLogin(String customerId);

    Nasabah updateNasabah(Nasabah nasabah);

    void  deleteNasabahById(String id);

    List<Nasabah> getNasabahByUserId(String userCredentialId);

    List<Nasabah> createAllNasabah(List<Nasabah> nasabah);
}
