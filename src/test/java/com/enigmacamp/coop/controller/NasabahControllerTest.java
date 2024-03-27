package com.enigmacamp.coop.controller;

import com.enigmacamp.coop.entity.Nasabah;
import com.enigmacamp.coop.model.request.NasabahRequest;
import com.enigmacamp.coop.model.response.PagingResponse;
import com.enigmacamp.coop.service.NasabahService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class NasabahControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NasabahService nasabahService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterSuccess() throws Exception {
        // Persiapan objek NasabahRequest
        NasabahRequest request = new NasabahRequest();
        request.setFullname("test");
        request.setPhoneNumber("081232222111");
        request.setEmail("Test@gmail.com");
        request.setAddress("Test");

        // Persiapan objek Nasabah yang diharapkan dikembalikan dari servis
        String expectedId = UUID.randomUUID().toString(); // UUID yang digenerate otomatis sebagai string
        Nasabah expectedNasabah = new Nasabah();
        expectedNasabah.setId(String.valueOf(UUID.fromString(expectedId)));

        // Simulasi panggilan servis ketika createNasabah dipanggil
        when(nasabahService.createNasabah(any(NasabahRequest.class))).thenReturn(expectedNasabah);

        // Memanggil endpoint dan melakukan pengujian pada respons
        mockMvc.perform(post("/api/v1/nasabah")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value("Success add data"))
                .andExpect(jsonPath("$.data.id").value(expectedId)); // Periksa string UUID

        // Memastikan bahwa metode createNasabah dipanggil tepat satu kali dengan argumen yang sesuai
        verify(nasabahService).createNasabah(any(NasabahRequest.class));
    }

    @Test
    void testRegisterBadRequest() throws Exception {
        // Persiapan objek NasabahRequest yang tidak valid (misalnya, tanpa fullname)
        NasabahRequest request = new NasabahRequest();
        request.setPhoneNumber("081232222111");
        request.setEmail("Test@gmail.com");
        request.setAddress("Test");

        // Memanggil endpoint dan memeriksa respons Bad Request
        mockMvc.perform(post("/api/v1/nasabah")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // Periksa status Bad Request
    }

    @Test
    void testGetAllNasabahSuccess() throws Exception {
        // Persiapan data nasabah
        List<Nasabah> nasabahList = new ArrayList<>();
        Nasabah nasabah1 = new Nasabah();
        // Inisialisasi nasabah1
        Nasabah nasabah2 = new Nasabah();
        // Inisialisasi nasabah2
        nasabahList.add(nasabah1);
        nasabahList.add(nasabah2);

        // Persiapan respons paging
        PagingResponse pagingResponse = new PagingResponse();
        pagingResponse.setPage(1);
        pagingResponse.setSize(10);
        pagingResponse.setTotalPages(1);
        pagingResponse.setTotalElement(2L);

        // Simulasi panggilan servis ketika getAllNasabah dipanggil
        when(nasabahService.getAllNasabah(any(Integer.class), any(Integer.class)))
                .thenReturn(new PageImpl<>(nasabahList));

        // Memanggil endpoint dan memeriksa respons OK
        mockMvc.perform(get("/api/v1/nasabah"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value("Success Get List data"))
                .andExpect(jsonPath("$.paging.page").value(1))
                .andExpect(jsonPath("$.paging.size").value(10))
                .andExpect(jsonPath("$.paging.totalPages").value(1))
                .andExpect(jsonPath("$.paging.totalElement").value(2))
                .andExpect(jsonPath("$.data", hasSize(2))); // Periksa jumlah nasabah dalam data
    }

    @Test
    void testGetNasabahByIdSuccess() throws Exception {
        // Persiapan objek Nasabah yang akan dikembalikan oleh servis
        Nasabah nasabah = new Nasabah();
        nasabah.setId(UUID.randomUUID().toString());
        nasabah.setFullname("John Doe");

        // Simulasikan pemanggilan servis ketika getNasabahById dipanggil
        when(nasabahService.getNasabahById(anyString())).thenReturn(nasabah);

        // Panggil endpoint dan periksa respons
        mockMvc.perform(get("/api/v1/nasabah/{id}", nasabah.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value("Success Get By Id"))
                .andExpect(jsonPath("$.data.id").value(nasabah.getId()))
                .andExpect(jsonPath("$.data.fullname").value(nasabah.getFullname()));
    }

    @Test
    void testGetNasabahByIdNotFound() throws Exception {
        // Simulasikan pemanggilan servis ketika getNasabahById dipanggil
        when(nasabahService.getNasabahById(anyString())).thenReturn(null);

        // Panggil endpoint dan periksa respons
        mockMvc.perform(get("/api/v1/nasabah/{id}", "nonexistent-id"))
                .andExpect(status().isNotFound());
    }



}
