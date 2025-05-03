package br.com.consentimento.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConsentExceptionHandler {

    @ExceptionHandler(ConsentException.class)
    public ResponseEntity<String> handleConsentException(ConsentException ex){
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    public ResponseEntity<String> handleNullPointerException(NullPointerException ex){
        return ResponseEntity.badRequest().body("Dados ausentes ou inconsistentes");
    }
}
