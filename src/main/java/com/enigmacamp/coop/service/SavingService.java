package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.Loan;
import com.enigmacamp.coop.entity.Saving;

import java.util.List;

public interface SavingService {
    Saving createSaving(Saving saving);
    List<Saving> getListSaving();
    Saving getSavingById(String id);

    Saving getSavingByNasabahId(String id);
}
