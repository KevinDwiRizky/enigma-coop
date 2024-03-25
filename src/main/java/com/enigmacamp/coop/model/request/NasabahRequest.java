package com.enigmacamp.coop.model.request;

import com.enigmacamp.coop.constant.NasabahStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NasabahRequest {
    private String fullname;
    private String phoneNumber;
    private String email;
    private String address;
}
