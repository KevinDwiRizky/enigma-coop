package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.WebResponse;
import com.enigmacamp.coop.service.NasabahService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nasabah")
@AllArgsConstructor
public class NasabahController {

    private final NasabahService nasabahService;

    @PostMapping
    public ResponseEntity<WebResponse<Nasabah>> createNasabah(@RequestBody Nasabah nasabah) {
        Nasabah newNasabah = nasabahService.createNasabah(nasabah);
        WebResponse<Nasabah> response = WebResponse.<Nasabah>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success add data")
                .data(newNasabah)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllNasabah(){
        List<Nasabah> nasabahList = nasabahService.getAllNasabah();
        WebResponse<List<Nasabah>> response = WebResponse.<List<Nasabah>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List data")
                .data(nasabahList)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getNasabahById(@PathVariable String id) {
        Nasabah findNasabah = nasabahService.getNasabahById(id);
        WebResponse<Nasabah> response = WebResponse.<Nasabah>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get By Id ")
                .data(findNasabah)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteNasabahById(@PathVariable String id){
        nasabahService.deleteNasabahById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Delete Nasabah By Id ")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateNasabahById(@RequestBody Nasabah nasabah){
        Nasabah updateNasabah = nasabahService.updateNasabah(nasabah);
        WebResponse<Nasabah> response = WebResponse.<Nasabah>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Update Nasabah By Id ")
                .data(updateNasabah)
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/batch")
    public ResponseEntity<WebResponse<List<Nasabah>>> createNasabah(@RequestBody List<Nasabah> nasabahList) {
        List<Nasabah> newNasabahList = nasabahService.createAllNasabah(nasabahList);
        WebResponse<List<Nasabah>> response = WebResponse.<List<Nasabah>>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success add data")
                .data(newNasabahList)
                .build();
        return ResponseEntity.ok(response);
    }


}
