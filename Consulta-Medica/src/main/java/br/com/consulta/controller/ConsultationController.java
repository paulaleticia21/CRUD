package br.com.consulta.controller;

import br.com.consulta.model.Consultation;
import br.com.consulta.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public List<Consultation> getAllConsultation(){
        return consultationService.getAllConsultation();
    }
    @PostMapping
    public Consultation createConsultation(@RequestBody Consultation consultation){
        return consultationService.createConsultation(consultation);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable String id){
        Consultation consultation = consultationService.getConsultationById(id);
        return consultation != null ? ResponseEntity.ok(consultation) :
        ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable String id){
        consultationService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }
}
