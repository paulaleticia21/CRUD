package br.com.consentimento.model;

import br.com.consentimento.exception.StatusConsent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consent implements Serializable {

    private String clientId;
    private String resourceId;
    private LocalDateTime dateConsent;
    private LocalDateTime dateExpiratio;
    private Long ttl;
    private StatusConsent status;
}
