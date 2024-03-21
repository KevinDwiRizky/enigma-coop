package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.TrxSaving;
import com.enigmacamp.coop.model.request.TrxSavingRequest;

import java.util.List;

public interface TrxSavingService {
    TrxSaving createTrxSaving(TrxSavingRequest trxSavingRequest);
    List<TrxSaving> getListTrxSaving();
}
