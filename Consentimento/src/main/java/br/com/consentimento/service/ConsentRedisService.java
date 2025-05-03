package br.com.consentimento.service;

import br.com.consentimento.model.Consent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ConsentRedisService {

    private static final String REDIS_PREFIX = "consentimento:";

    private static final Duration TTL = Duration.ofDays(5);

    private final RedisTemplate<String, Object> redisTemplate;

    public Consent getConsent(String clientId){
        Object cached = redisTemplate.opsForValue().get(REDIS_PREFIX + clientId);
        return (cached instanceof Consent) ? (Consent) cached : null;
    }

    public void save(Consent consent){
        redisTemplate.opsForValue().set(REDIS_PREFIX + consent.getClientId(),consent, TTL);
    }
}
