package br.com.consentimento.model;

import br.com.consentimento.exception.StatusConsent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consent {

    private String clientId;
    private LocalDate dateconsent;
    private StatusConsent status;
}
