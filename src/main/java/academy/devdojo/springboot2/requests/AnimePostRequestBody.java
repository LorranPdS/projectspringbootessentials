package academy.devdojo.springboot2.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

// ela nada mais é que uma classe DTO
@Data
public class AnimePostRequestBody {

    @NotBlank(message = "The anime's name cannot be empty or null")
    private String name;
    @URL(message = "The URL is not valid")
    private String url;
}
/* Agora você verá no stacktrace no campo "fields" que ela mostra
 * o erro nos dois atributos em vez de um só atributo (haja vista provocarmos
 * o erro na 'url' colocando um valor inválido e deixando o 'name' vazio)
 */