package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.repository.NasabahRepository;
import com.enigmacamp.coop.service.NasabahService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NasabahServiceImpl implements NasabahService {

    private NasabahRepository nasabahRepository;

    @Override
    public Nasabah createNasabah(Nasabah nasabah) {
        return nasabahRepository.saveAndFlush(nasabah);
    }

    @Override
    public Page<Nasabah> getAllNasabah(Integer page, Integer size) {
        // page ke berapa
        // sizenya berapa perpages

        if (page <=0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return nasabahRepository.findAll(pageable);
    }

    @Override
    public Nasabah getNasabahById(String id) {
        Optional<Nasabah> optionalNasabah=nasabahRepository.findById(id);
        if (optionalNasabah.isPresent()) return optionalNasabah.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nasabah with id : " + id + " Not Found");
    }

    @Override
    public Nasabah updateNasabah(Nasabah nasabah) {
        this.getNasabahById(nasabah.getId());
        return nasabahRepository.save(nasabah);
    }

    @Override
    public void deleteNasabahById(String id) {
        this.getNasabahById(id);
        nasabahRepository.deleteById(id);
    }

    @Override
    public List<Nasabah> createAllNasabah(List<Nasabah> nasabahList) {
        return nasabahRepository.saveAll(nasabahList);
    }
}
