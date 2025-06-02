package br.com.consentimento.controller;

import br.com.consentimento.model.Consent;
import br.com.consentimento.service.ConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consent/v1")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentService service;

    @PostMapping("/{clientId}")
    public Consent darConsent(@PathVariable String clientId) {
        return service.darConsent(clientId);
    }

    @GetMapping("/{clientId}/{resourceId}")
    public Consent consultarConsent(
            @PathVariable String clientId,
            @PathVariable String resourceId
    ) {
        return service.consultarConsent(clientId, resourceId);
    }
}
