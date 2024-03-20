package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.model.Nasabah;
import com.enigmacamp.coop.repository.NasabahRepository;
import com.enigmacamp.coop.service.NasabahService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NasabahServiceImpl implements NasabahService {

    private NasabahRepository nasabahRepository;

    @Override
    public Nasabah createNasabah(Nasabah nasabah) {
        return nasabahRepository.saveAndFlush(nasabah);
    }

    @Override
    public List<Nasabah> getAllNasabah() {
        return nasabahRepository.findAll();
    }
}
