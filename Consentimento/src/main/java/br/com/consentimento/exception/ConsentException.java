package br.com.consentimento.exception;

import org.springframework.http.HttpStatus;

public class ConsentException extends RuntimeException{

    private final HttpStatus status;
    public ConsentException(String message,HttpStatus status){
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus(){
        return status;
    }
}
