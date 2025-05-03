package br.com.consulta.config.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    private static final Logger log = LoggerFactory.getLogger(DynamoDBConfig.class);

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        try {
            log.info("Criando a Instancia do Dynamodb");
            AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
                    .build();
            log.info("Instancia Dynamodb criada com sucesso");
            return dynamoDB;
        } catch (Exception e) {
            log.error("Erro ao criar a tabela", e);
            throw new RuntimeException("Falha de configurar a tabela ", e);
        }
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        try {
            log.info("Criando DynamoDBMapper");
            DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB());
            log.info("DynamoDBMapper foi criado com sucesso");
            return mapper;
        } catch (Exception e) {
            log.warn("Aviso: Falha ao criar DynamoDBMapper, utilizando uma instância nula.", e);
        }
        return null;
    }
}
