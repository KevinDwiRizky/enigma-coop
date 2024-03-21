package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.entity.TrxSaving;
import com.enigmacamp.coop.entity.TrxSaving;
import com.enigmacamp.coop.model.response.WebResponse;
import com.enigmacamp.coop.service.TrxSavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trx-saving")
@AllArgsConstructor
public class TrxSavingController {
    private final TrxSavingService trxSavingService;

    @PostMapping
    public ResponseEntity<WebResponse<TrxSaving>> createTrxSaving(@RequestBody TrxSaving trxSaving) {
        TrxSaving newTrxSaving = trxSavingService.createTrxSaving(trxSaving);
        WebResponse<TrxSaving> response = WebResponse.<TrxSaving>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success Trasaction Saving")
                .data(newTrxSaving)
                .build();
        return ResponseEntity.ok(response);
    }

}