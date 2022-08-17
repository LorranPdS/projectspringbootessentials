package academy.devdojo.springboot2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Como eu estou dizendo que essa exceção é uma exceção BadRequestException,
 * eu gostaria que essa exceção sempre retornasse um status http BadRequest,
 * e por isso foi usada abaixo a annotation @ResponseStatus com o status BAD_REQUEST
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message){
        super(message);
    }
}

/* Antes, eram apresentados 4 campos ao dar o Bad Request no Postman, que eram:
timestamp, status, error, message e path. Agora nós colocaremos outros campos
sempre que for uma exceção BadRequest
 */