package academy.devdojo.springboot2.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter // utilizo apenas os getters
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails {

}
