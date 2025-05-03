package br.com.consulta.controller;

import br.com.consulta.model.Consultation;
import br.com.consulta.service.ConsultationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultations")
public class ConsultationController {

    private static final Logger logger = LoggerFactory.getLogger(ConsultationController.class);

    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultation() {
        logger.info("Buscando todas as consultas...");
        List<Consultation> consultations = consultationService.getAllConsultation();
        if (consultations.isEmpty()) {
            logger.warn("Nenhuma consulta encontrada.");
            return ResponseEntity.noContent().build();
        }
        logger.info("Consultas encontradas: {}", consultations.size());
        return ResponseEntity.ok(consultations);
    }

    @PostMapping
    public ResponseEntity<Consultation> createConsultation(@RequestBody Consultation consultation) {
        if (consultation == null) {
            logger.error("Consulta recebida é nula.");
            return ResponseEntity.badRequest().build();
        }
        logger.info("Criando nova consulta: {}", consultation);
        try {
            Consultation createdConsultation = consultationService.createConsultation(consultation);
            return ResponseEntity.ok(createdConsultation);

        } catch (Exception e) {
            logger.error("Erro ao criar consulta: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable String id) {
        logger.info("Buscando consulta com ID: {}", id);
        try {
            Consultation consultation = consultationService.getConsultationById(id);
            if (consultation != null) {
                return ResponseEntity.ok(consultation);
            } else {
                logger.warn("Consulta com ID: {} não encontrada.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar consulta por ID: {}", id, e);
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable String id) {
        logger.info("Deletando consulta com ID: {}", id);
        try {
            consultationService.deleteConsultation(id);
            logger.info("Consulta com ID: {} deletada com sucesso.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar consulta com ID: {}", id, e);
            return ResponseEntity.status(500).build();
        }
    }
}