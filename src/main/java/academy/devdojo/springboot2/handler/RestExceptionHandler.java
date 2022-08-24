package academy.devdojo.springboot2.handler;

import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.exception.BadRequestExceptionDetails;
import academy.devdojo.springboot2.exception.ValidationExceptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
Quando você usa a anotação ControllerAdvice, você está dizendo para todos os
controllers que eles devem utilizar o que você for colocar dentro dessa classe
aqui baseado como se fosse numa Flag. A Flag que iremos utilizar é a @ExceptionHandler
 */
@ControllerAdvice
@Log4j2
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

    /*Antes foi necessário descobrirmos qual será a exceção a ser posta no
    * @ExceptionHandler, e para descobrirmos, fizemos um post da seguinte forma:
    * http://localhost:8080/animes?trace=true
    * Enviar no body o "name":null
    * Então vá testando da forma como está acima
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception){
        // Colocamos isso abaixo só para vermos no debug os campos dele no terminal
//        log.info("Fields {}", exception.getBindingResult().getFieldError().getField());

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields")
                        .details("Check the field(s) error")
                        .developerMessage(exception.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
