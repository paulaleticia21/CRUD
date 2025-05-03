package br.com.consulta.service;

import br.com.consulta.model.Consultation;
import br.com.consulta.repository.ConsultationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    private static final Logger log = LoggerFactory.getLogger(ConsultationService.class);

    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation createConsultation(Consultation consultation){

        if (consultation  == null){
            log.error("Consulta a ser criada não pode ser nula.");
            throw new IllegalArgumentException("Consulta não pode ser nula");
        }
        try {
            consultationRepository.save(consultation);
            log.info("Consulta criada com sucesso: {}",consultation);
            return consultation;
        }catch (Exception e){
            log.error("Erro ao criar consulta: ", e);
            throw new RuntimeException("Falha ao criar consulta", e);
        }
    }
    public Consultation getConsultationById(String id){

        if (id == null) {
            log.error("ID da consulta não pode ser nulo ao buscar.");
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        try {
            Consultation consultation = consultationRepository.findById(id);
            if (consultation == null) {
                log.warn("Consulta com ID: {} não encontrada.", id);
                return consultation;
            }
        } catch (Exception e) {
            log.error("Erro ao buscar consulta por ID: {}", id, e);
            throw new RuntimeException("Erro ao buscar consulta", e);
        }
        return null;
    }
    public List<Consultation>getAllConsultation(){
        try {
            List<Consultation> consultations = consultationRepository.findAll();
            log.info("Consultas retornadas: {}", consultations.size());
            return consultations;

        } catch (Exception e) {
            log.error("Erro ao buscar todas as consultas: ", e);
            throw new RuntimeException("Erro ao buscar todas as consultas", e);
        }
    }
    public void deleteConsultation(String id) {
        if (id == null) {
            log.error("ID da consulta não pode ser nulo ao deletar.");
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        try {
            consultationRepository.deleteById(id);
            log.info("Consulta com ID: {} deletada com sucesso.", id);

        } catch (Exception e) {
            log.error("Erro ao deletar consulta com ID: {}", id, e);
            throw new RuntimeException("Erro ao deletar consulta", e);
        }
    }
}
