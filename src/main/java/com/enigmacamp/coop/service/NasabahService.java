package com.enigmacamp.coop.service;

import com.enigmacamp.coop.model.Nasabah;
import com.enigmacamp.coop.repository.NasabahRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NasabahService {
    private final NasabahRepository nasabahRepository;

    public Nasabah registerNewNasabah(Nasabah nasabah){
        return nasabahRepository.saveAndFlush(nasabah);
    }

    public List<Nasabah> getAllNasabah(){
        return nasabahRepository.findAll();
    }



}
