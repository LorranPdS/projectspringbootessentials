package academy.devdojo.springboot2.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

// ela nada mais é que uma classe DTO
@Data
public class AnimePostRequestBody {

    @NotBlank(message = "The anime's name cannot be empty or null")
    private String name;
}
