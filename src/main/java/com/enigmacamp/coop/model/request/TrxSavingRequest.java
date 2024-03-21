package com.enigmacamp.coop.model.request;

import com.enigmacamp.coop.constant.SavingType;
import lombok.*;
import org.springframework.web.server.WebSession;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrxSavingRequest {
    private Long amount;
    private String savingId;
    private SavingType savingType;
    private Date date;


}
