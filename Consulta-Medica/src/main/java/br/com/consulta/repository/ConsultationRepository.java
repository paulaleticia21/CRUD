package br.com.consulta.repository;

import br.com.consulta.model.Consultation;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConsultationRepository {

    private final DynamoDBMapper dynamonDBMapper;

    @Autowired
    public ConsultationRepository(DynamoDBMapper dynamonDBMapper){
        this.dynamonDBMapper =dynamonDBMapper;
    }
    public void save(Consultation consultation){
        dynamonDBMapper.save(consultation);
    }
    public Consultation findById(String id){
        return dynamonDBMapper.load(Consultation.class, id);
    }
    public void deleteById(String id){
      Consultation consultation = findById(id);
      if(consultation != null ){
            dynamonDBMapper.delete(consultation);
      }
    }
    public List<Consultation> findAll(){
        return dynamonDBMapper.scan(Consultation.class, new DynamoDBScanExpression());
    }
}
