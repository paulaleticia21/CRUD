package br.com.consulta.service;

import br.com.consulta.model.Consultation;
import br.com.consulta.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation createConsultation(Consultation consultation){
        consultationRepository.save(consultation);
        return consultation;
    }
    public Consultation getConsultationById(String id){
        return consultationRepository.findById(id);
    }
    public List<Consultation>getAllConsultation(){
        return consultationRepository.findAll();
    }
    public void deleteConsultation(String id){
        consultationRepository.deleteById(id);
    }

}
