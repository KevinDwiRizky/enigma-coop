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
        return nasabahService.createNasabah(nasabah);
    }

    @GetMapping
    public List<Nasabah> getAllNasabah(){
        return nasabahService.getAllNasabah();
    }

    @GetMapping(path = "/{id}")
    public Nasabah getNasabahById(@PathVariable String id){
        return nasabahService.getNasabahById(id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteNasabahById(@PathVariable String id){
        nasabahService.getNasabahById(id);
        return "Success delete Nasabah";
    }

    @PutMapping
    public Nasabah updateNasabahById(@RequestBody Nasabah nasabah){
        return nasabahService.updateNasabah(nasabah);
    }

    @PostMapping("/batch")
    public List<Nasabah> createNasabah(@RequestBody List<Nasabah> nasabah) {
        return nasabahService.createAllNasabah(nasabah);
    }

}
