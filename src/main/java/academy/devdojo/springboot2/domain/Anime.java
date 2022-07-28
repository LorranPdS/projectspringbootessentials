package academy.devdojo.springboot2.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Anime {

    private Long id;
    // @JsonProperty("nameCharacter") o nome do atributo em JSON fica assim mas na base fica outro
    private String nome;


}
