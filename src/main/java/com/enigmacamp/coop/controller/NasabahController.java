package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.NasabahRequest;
import com.enigmacamp.coop.model.response.NasabahResponse;
import com.enigmacamp.coop.model.response.PagingResponse;
import com.enigmacamp.coop.model.response.WebResponse;
import com.enigmacamp.coop.service.AuthService;
import com.enigmacamp.coop.service.NasabahService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/nasabah")
public class NasabahController {


    private final NasabahService nasabahService;


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllNasabah(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<Nasabah> nasabahList = nasabahService.getAllNasabah(page, size);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page).size(size)
                .totalPages(nasabahList.getTotalPages())
                .totalElement(nasabahList.getTotalElements())
                .build();

        WebResponse<List<Nasabah>> response = WebResponse.<List<Nasabah>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List data")
                .paging(pagingResponse)
                .data(nasabahList.getContent())
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
