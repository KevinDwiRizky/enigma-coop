package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.Nasabah;

import java.util.List;

public interface NasabahService {
    Nasabah createNasabah(Nasabah nasabah);

    List<Nasabah> getAllNasabah();

    Nasabah getNasabahById(String id);

    Nasabah updateNasabah(Nasabah nasabah);

    void  deleteNasabahById(String id);

    List<Nasabah> createAllNasabah(List<Nasabah> nasabah);
}
