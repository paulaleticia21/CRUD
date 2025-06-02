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

    public static ConsentException ativo(){
        return new ConsentException("O consentimento está ativo.", HttpStatus.CONFLICT);
    }
    public static ConsentException inativo(){
        return new ConsentException("O consentimento esta inativo.", HttpStatus.BAD_REQUEST);
    }
    public static ConsentException vencido(){
        return new ConsentException("O consentimento esta vencido", HttpStatus.GONE);
    }
    public static ConsentException naoEncontrado(){
        return new ConsentException("O consnetimento não foi encontrado",HttpStatus.NOT_FOUND);
    }
}
