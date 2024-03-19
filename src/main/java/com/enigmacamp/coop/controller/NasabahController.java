package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.model.Nasabah;
import com.enigmacamp.coop.service.NasabahService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nasabah")
@AllArgsConstructor
public class NasabahController {

    private final NasabahService nasabahService;

    @PostMapping
    public Nasabah createNasabah(@RequestBody Nasabah nasabah) {
        return nasabahService.registerNewNasabah(nasabah);
    }

    @GetMapping
    public List<Nasabah> getAllNasabah(){
        return nasabahService.getAllNasabah();
    }
}
