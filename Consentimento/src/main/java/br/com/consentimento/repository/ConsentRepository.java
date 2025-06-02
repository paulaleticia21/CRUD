package br.com.consentimento.repository;

import br.com.consentimento.model.Consent;

import java.util.Optional;

public interface ConsentRepository {

    Optional<Consent>findByClientIdResourceId(String clientId, String resourceId);
    void save(Consent consent);
}