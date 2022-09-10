package academy.devdojo.springboot2.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

// ela nada mais é que uma classe DTO
@Data
@Builder // 11) Vamos fazer desse objeto um builder
public class AnimePostRequestBody {

    @NotBlank(message = "The anime's name cannot be empty or null")
    private String name;
}
