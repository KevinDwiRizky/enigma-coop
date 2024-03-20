package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.constant.SavingType;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.entity.Saving;
import com.enigmacamp.coop.entity.TrxSaving;
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
    private final NasabahService nasabahService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TrxSaving createTrxSaving(TrxSaving trxSaving) {
        // cek saving ke db
        Saving saving = savingService.getSavingById(trxSaving.getSaving().getId());
        // cek nasabah ke db
        Nasabah nasabah = nasabahService.getNasabahById(trxSaving.getSaving().getNasabah().getId());
        // cek topup atau penarikan saldo
        if (trxSaving.getSavingType().equals(SavingType.DEBIT)){
            saving.setBalance(saving.getBalance()+trxSaving.getAmount());
        } else {
            if (trxSaving.getAmount() < saving.getBalance()) {
                saving.setBalance(saving.getBalance() - trxSaving.getAmount());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo tidak cukup !");
            }
        }
        return trxSavingRepository.saveAndFlush(trxSaving);
    }

    @Override
    public List<TrxSaving> getListTrxSaving() {
        return null;
    }
}
