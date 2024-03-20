package com.enigmacamp.coop.service;

import com.enigmacamp.coop.model.Nasabah;

import java.util.List;

public interface NasabahService {
    Nasabah createNasabah(Nasabah nasabah);
    List<Nasabah> getAllNasabah();
}
