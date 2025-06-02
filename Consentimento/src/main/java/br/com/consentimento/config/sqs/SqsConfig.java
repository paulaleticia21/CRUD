package br.com.consentimento.config.sqs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    @Value("${aws.sqs.endpoint}")
    private String sqsEndpoint;
    @Value("${aws.sqs.region}")
    private String region;
    @Value("${aws.dynamodb.access-key-id}")
    private String accessKeyId;
    @Value("${aws.dynamodb.secret-access-key}")
    private String secretAccessKey;

    public SqsClient sqsClient(){
        return SqsClient.builder()
                .endpointOverride(
                        URI.create(sqsEndpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId,secretAccessKey)))
                .build();
    }
}
