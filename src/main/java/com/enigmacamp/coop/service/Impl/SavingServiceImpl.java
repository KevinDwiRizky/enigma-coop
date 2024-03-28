package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Saving;
import com.enigmacamp.coop.repository.SavingRepository;
import com.enigmacamp.coop.service.SavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final SavingRepository savingRepository;

    @Override
    public Saving createSaving(Saving saving) {
        return savingRepository.saveAndFlush(saving);
    }

    @Override
    public List<Saving> getListSaving() {
        return savingRepository.findAll();
    }

    @Override
    public Saving getSavingById(String id) {
        Optional<Saving> optionalSaving =savingRepository.findById(id);
        if (optionalSaving.isPresent()) return optionalSaving.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Saving ID : " + id +" not found");
    }

    @Override
    public Saving getSavingByNasabahId(String id) {
        return savingRepository.findByNasabahId(id);
    }
}
