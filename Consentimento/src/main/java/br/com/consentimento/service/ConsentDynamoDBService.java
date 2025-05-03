package br.com.consentimento.service;

import br.com.consentimento.config.dynamodb.DynamoDBConfig;
import br.com.consentimento.exception.StatusConsent;
import br.com.consentimento.model.Consent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

@Service
@RequiredArgsConstructor
public class ConsentDynamoDBService {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoDBConfig dynamoDBConfig;

    public Consent getConsent(String clientId){
        Map<String, Attributes> key = new HashMap<>();
        key.put("clientId", AttributeValue.builder().s(clientId).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName(dynamoDBConfig.getTableName();
                .key(key)
                .build();

        Map<String, AttributeValue> item = dynamoDbClient.getItem(request).item();

        if (item == null || item.isEmpty()) return null;

        return Consent.builder()
                .clientId(item.get("clintId").s())
                .dateconsent(LocalDateTime.parse(item.get("dataConsentimento").s()))
                .status(StatusConsent.valueOf(item.get("status").s()))
                .build();
    }

    public void save(Consent consent){
        Map<String,AttributeValue> item = new HashMap<>();
        item.put("clientId", AttributeValue.builder().s(consent.getClientId()).build());
        item.put("dataConsentimento", AttributeValue.builder().s(consent.getDateconsent().toString()).build());
        item.put("status",AttributeValue.builder().s(consent.getStatus().name()).build());
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(dynamoDBConfig.getTableName())
                .item(item)
                .build();
        dynamoDbClient.putItem(putItemRequest);
    }

}
