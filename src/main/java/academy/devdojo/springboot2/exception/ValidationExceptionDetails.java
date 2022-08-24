package academy.devdojo.springboot2.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final String fields;
    private final String fieldsMessage;
}
/* Feito isso, agora vamos criar o Handler. Lembrando que o Handler é
* uma forma de nós interceptarmos e dizermos para o Controller
* interceptar as exceções e adicionar o que você definiu dentro
* do método */