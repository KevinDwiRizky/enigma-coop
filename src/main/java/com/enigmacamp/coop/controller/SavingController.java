package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.entity.Saving;
import com.enigmacamp.coop.model.response.PagingResponse;
import com.enigmacamp.coop.model.response.WebResponse;
import com.enigmacamp.coop.service.SavingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saving")
@AllArgsConstructor
public class SavingController {
    private final SavingService savingService;

    @PostMapping
    public ResponseEntity<WebResponse<Saving>> createSaving(@RequestBody Saving saving) {
        Saving newSaving = savingService.createSaving(saving);
        WebResponse<Saving> response = WebResponse.<Saving>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success add data")
                .data(newSaving)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllNasabah(){
        List<Saving> savingList = savingService.getListSaving();

        WebResponse<List<Saving>> response = WebResponse.<List<Saving>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List data")
                .data(savingList)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getNasabahById(@PathVariable String id) {
        Saving findSaving = savingService.getSavingById(id);
        WebResponse<Saving> response = WebResponse.<Saving>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get By Id ")
                .data(findSaving)
                .build();
        return ResponseEntity.ok(response);
    }
}
