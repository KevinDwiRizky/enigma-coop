package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.Nasabah;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NasabahService {
    Nasabah createNasabah(Nasabah nasabah);

    Page<Nasabah> getAllNasabah(Integer page, Integer size);

    Nasabah getNasabahById(String id);

    Nasabah updateNasabah(Nasabah nasabah);

    void  deleteNasabahById(String id);

    List<Nasabah> createAllNasabah(List<Nasabah> nasabah);
}
