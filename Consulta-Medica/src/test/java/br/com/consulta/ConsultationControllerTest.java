package br.com.consulta;

import br.com.consulta.controller.ConsultationController;
import br.com.consulta.model.Consultation;
import br.com.consulta.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ConsultationControllerTest {
    @Mock
    private ConsultationService consultationService;

    @InjectMocks
    private ConsultationController consultationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateConsultation() {
        Consultation consultation = new Consultation();
        consultation.setId("1");
        consultation.setPatientName("Paula Leticia");
        consultation.setDoctorName("Dr. Smith");
        consultation.setDate(LocalDate.parse("2025-09-08")); 

        when(consultationService.createConsultation(any(Consultation.class))).thenReturn(consultation);

        Consultation createdConsultation = consultationController.createConsultation(consultation).getBody();

        assertEquals("Paula Leticia", createdConsultation.getPatientName());
        verify(consultationService, times(1)).createConsultation(consultation);
    }

    @Test
    public void testGetConsultationById() {
        Consultation consultation = new Consultation();
        consultation.setId("1");
        consultation.setPatientName("Paula Leticia");

        when(consultationService.getConsultationById("1")).thenReturn(consultation);

        ResponseEntity<Consultation> response = consultationController.getConsultationById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Paula Leticia", response.getBody().getPatientName());
    }

    @Test
    public void testDeleteConsultation() {
        doNothing().when(consultationService).deleteConsultation("1");

        ResponseEntity<Void> response = consultationController.deleteConsultation("1");

        assertEquals(204, response.getStatusCodeValue());
        verify(consultationService, times(1)).deleteConsultation("1");
    }
}
