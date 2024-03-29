package com.enigmacamp.coop.service.Impl;

import com.enigmacamp.coop.constant.NasabahStatus;
import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.entity.Saving;
import com.enigmacamp.coop.model.request.NasabahRequest;
import com.enigmacamp.coop.repository.NasabahRepository;
import com.enigmacamp.coop.service.NasabahService;
import com.enigmacamp.coop.service.SavingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NasabahServiceImpl implements NasabahService {

    private final NasabahRepository nasabahRepository;
    private final SavingService savingService;



    @Override
    public Nasabah createNasabah(@Valid @RequestBody NasabahRequest nasabahRequest) {

        // setiap register akan dibuatkan saving otomatis
        Nasabah newNasabah = Nasabah.builder()
                .fullname(nasabahRequest.getFullname())
                .email(nasabahRequest.getEmail())
                .phoneNumber(nasabahRequest.getPhoneNumber())
                .address(nasabahRequest.getAddress())
                .status(NasabahStatus.ACTIVE)
                .build();
//        Nasabah newNasabah = nasabahRepository.saveAndFlush(nasabahRequest);
        Saving newSaving = Saving.builder()
                        .balance(0L)
                        .nasabah(newNasabah)
                        .build();
        savingService.createSaving(newSaving);
        return newNasabah;
    }

    @Override
    public Page<Nasabah> getAllNasabah(Integer page, Integer size) {
        // page ke berapa
        // sizenya berapa perpages

        if (page <=0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return nasabahRepository.findAll(pageable);
    }

    @Override
    public Nasabah getNasabahById(String id) {
        Optional<Nasabah> optionalNasabah=nasabahRepository.findById(id);
        if (optionalNasabah.isPresent()) return optionalNasabah.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nasabah with id : " + id + " Not Found");
    }

    @Override
    public Nasabah getNasabahByLogin(String nasabahId) {
        // Mendapatkan informasi autentikasi pengguna yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Mengambil nama pengguna (ID) dari informasi autentikasi
        String loggedInUserId = authentication.getName();

        // Memeriksa apakah ID pengguna yang sedang login cocok dengan ID yang diberikan dalam parameter
        if (!loggedInUserId.equals(nasabahId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }

        Optional<Nasabah> optionalNasabah = nasabahRepository.findById(loggedInUserId);
        if (optionalNasabah.isPresent()) {
            return optionalNasabah.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nasabah with id : " + nasabahId + " Not Found");
        }
    }


    @Override
    public Nasabah updateNasabah(Nasabah nasabah) {
        this.getNasabahById(nasabah.getId());
        return nasabahRepository.save(nasabah);
    }

    @Override
    public void deleteNasabahById(String id) {
        this.getNasabahById(id);
        nasabahRepository.deleteById(id);
    }

    @Override
    public List<Nasabah> createAllNasabah(List<Nasabah> nasabahList) {
        return nasabahRepository.saveAll(nasabahList);
    }
}
