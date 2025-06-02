package br.com.consentimento.service;

import br.com.consentimento.exception.ConsentException;
import br.com.consentimento.exception.StatusConsent;
import br.com.consentimento.model.Consent;
import br.com.consentimento.repository.ConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.endpoints.internal.Value;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ConsentService {


    private final ConsentRepository repository;
    private final RedisTemplate<String, Consent> redisTemplate;
    private final SqsClient sqsClient;

    private static final String SQS_QUEUE_URL = "http://localhost:4566/000000000000/consentimento-inativo-queue";

    private static final long REDIS_TTL = 5 * 24 * 60 * 60;

    public Consent darConsent(String clientId){
        String resourceId = UUID.randomUUID().toString();
        LocalDateTime localDateTime = LocalDateTime.now();

        Consent consent = Consent.builder()
                .clientId(clientId)
                .resourceId(resourceId)
                .dateConsent(localDateTime)
                .dateExpiratio(localDateTime.plusDays(30))
                .status(StatusConsent.ATIVO)
                .ttl(localDateTime.plusDays(30).atZone(
                        ZoneId.systemDefault()).toEpochSecond())
                .build();
        repository.save(consent);
        return consent;
    }
    public Consent consultarConsent(String clientId, String resourceId){
        String redisKey = clientId + ":" + resourceId;
        Consent cached = redisTemplate.opsForValue().get(redisKey);

        if (cached != null){
            return processarConsent(cached, redisKey);
        }
    Optional<Consent> opt = repository.findByClientIdResourceId(clientId, resourceId);
    Consent consent = opt.orElseThrow(ConsentException::naoEncontrado);

        return processarConsent(consent, redisKey);
}

private Consent processarConsent(Consent consent, String redisKey) {
    long days = ChronoUnit.DAYS.between(consent.getDateConsent(), LocalDateTime.now());

    if (consent.getStatus() == StatusConsent.ATIVO) {
        if (days > 30 || LocalDateTime.now().isAfter(consent.getDateExpiratio())) {
            consent.setStatus(StatusConsent.INATIVO);
            redisTemplate.opsForValue().set(redisKey, consent, ConsentService.REDIS_TTL, TimeUnit.SECONDS);

            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(ConsentService.SQS_QUEUE_URL)
                    .messageBody("Consentimento inativado: " + consent.getClientId() + ", " + consent.getResourceId())
                    .build()
            );
            throw ConsentException.vencido();
        } else {
            throw ConsentException.ativo();
        }
    } else if (consent.getStatus() == StatusConsent.INATIVO) {
        redisTemplate.opsForValue().set(redisKey, consent, ConsentService.REDIS_TTL, TimeUnit.SECONDS);

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(ConsentService.SQS_QUEUE_URL)
                .messageBody("Consentimento inativado: " + consent.getClientId() + ", " + consent.getResourceId())
                .build()
        );
        throw ConsentException.inativo();
    }
    return consent;

    }
}