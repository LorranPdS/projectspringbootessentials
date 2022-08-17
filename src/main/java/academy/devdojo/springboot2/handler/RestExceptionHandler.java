package academy.devdojo.springboot2.handler;

import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/*
Quando você usa a anotação ControllerAdvice, você está dizendo para todos os
controllers que eles devem utilizar o que você for colocar dentro dessa classe
aqui baseado como se fosse numa Flag. A Flag que iremos utilizar é a @ExceptionHandler
 */
@ControllerAdvice
public class RestExceptionHandler {
    /*
    Com a anotação da flag ExceptionHandler você está dizendo: se você lançar
     uma exceção e ela for do tipo BadRequestException, você utilizará o
     método que está essa anotação (handlerBadRequestException) e retornar
     esse valor (ResponseEntity<BadRequestExceptionDetails>).
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
