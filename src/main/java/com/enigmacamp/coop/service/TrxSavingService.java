package com.enigmacamp.coop.service;

import com.enigmacamp.coop.entity.TrxSaving;

import java.util.List;

public interface TrxSavingService {
    TrxSaving createTrxSaving(TrxSaving trxSaving);
    List<TrxSaving> getListTrxSaving();
}
