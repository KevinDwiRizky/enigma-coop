package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.constant.SavingType;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.entity.Saving;
import com.enigmacamp.coop.entity.TrxSaving;
import com.enigmacamp.coop.model.request.TrxSavingRequest;
import com.enigmacamp.coop.repository.TrxSavingRepository;
import com.enigmacamp.coop.service.NasabahService;
import com.enigmacamp.coop.service.SavingService;
import com.enigmacamp.coop.service.TrxSavingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TrxSavingImpl implements TrxSavingService {

    private final TrxSavingRepository trxSavingRepository;
    private final SavingService savingService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TrxSaving createTrxSaving(TrxSavingRequest trxSavingRequest) {
        Saving saving = savingService.getSavingById(trxSavingRequest.getSavingId());
        // cek topup atau penarikan saldo
        if (trxSavingRequest.getSavingType().equals(SavingType.DEBIT)){
            saving.setBalance(saving.getBalance()+trxSavingRequest.getAmount());
        } else {
            if (trxSavingRequest.getAmount() < saving.getBalance()) {
                saving.setBalance(saving.getBalance()-trxSavingRequest.getAmount());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo tidak cukup !");
            }
        }
        TrxSaving newTrxSaving = TrxSaving.builder()
                .amount(trxSavingRequest.getAmount())
                .saving(saving)
                .savingType(trxSavingRequest.getSavingType())
                .build();
        return trxSavingRepository.saveAndFlush(newTrxSaving);
    }

    @Override
    public List<TrxSaving> getListTrxSaving() {
        return trxSavingRepository.findAll();
    }
}
