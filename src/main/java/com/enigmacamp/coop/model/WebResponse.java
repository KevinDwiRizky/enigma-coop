package com.enigmacamp.coop.model;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class WebResponse<T> {
    private String status;
    private String message;
    private T data;
}
