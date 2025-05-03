package br.com.consulta.repository;

import br.com.consulta.model.Consultation;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ConsultationRepository {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConsultationRepository.class);
    private final DynamoDBMapper dynamonDBMapper;

    @Autowired
    public ConsultationRepository(DynamoDBMapper dynamonDBMapper){
        this.dynamonDBMapper =dynamonDBMapper;
    }
    public void save(Consultation consultation){
        if(consultation == null){
            log.error("Tentativa de salvar consulta nula.");
        throw new IllegalArgumentException("Consulta não pode ser nula");
    }try{
            dynamonDBMapper.save(consultation);
            log.info("Consulta salva com sucesso:{}", consultation);
        }catch (Exception e){
            log.error("Erro ao salvar consulta", e);
            throw new RuntimeException("Falha ao salvar consulta", e);
        }
    }

    public Consultation findById(String id){
        if (id == null){
            log.error("ID da consulta não pode ser nulo.");
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        try{
            Consultation consultation = dynamonDBMapper.load(Consultation.class, id);
            log.info("Consulta encontrada{}", consultation);
            return consultation;
        }catch (Exception e){
            log.error("Erro ao buscar consulta por ID: {}", id, e);
            return null;
        }
    }
    public void deleteById(String id){
        if (id == null){
            log.error("ID da consulta não pode ser nulo ao deletar.");
            throw new IllegalArgumentException("ID não pode ser nulo");
        }try{
            Consultation consultation = findById(id);
            if (consultation != null){
                dynamonDBMapper.delete(consultation);
                log.info("Consulta deletada com sucesso: {}", consultation);
            }else {
                log.warn("Consulta com ID: {} não encontrada para deletar.", id);
            }
        }catch (Exception e){
            log.error("Erro ao deletar consulta ID{}", id, e);
            throw new RuntimeException("Falha ao deletar consulta", e);
        }

    }
    public List<Consultation> findAll(){
        try {
            List<Consultation> consultations = dynamonDBMapper.scan(Consultation.class, new DynamoDBScanExpression());
            log.error("Total de consultas encontradas: {}", consultations.size());
            return consultations;
        }catch (Exception e){
            log.error("Erro ao buscar todas as consultas", e);
            throw new RuntimeException("Falha ao buscar consulta",e);
        }
    }
}
