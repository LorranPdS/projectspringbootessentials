package academy.devdojo.springboot2.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

// ela nada mais é que uma classe DTO
@Data
public class AnimePostRequestBody {
    /* 4) Com a annotation @NotEmpty, você está dizendo que o campo name não pode ser
    informado pelo usuário vazio ou nulo, e você ainda pode usar um parâmetro
    dessa annotation para passar uma mensagem caso seja enviada assim, que foi o
    que fizemos abaixo (porém se o usuário colocar um espaço vazio e salvar, ele salvará
    com o espaço vazio). Portanto, achei melhor colocar a annotation @NotBlank que, se o
    usuário também colocar um espaço e salvar, a annotation não vai permitir que seja salvo

    5) Inclusive, há vários outros tipos de validações, como para URL, quantidade mínima
    e máxima para números, enfim, vale à pena dar uma explorada nessas possibilidades
     */
    //@NotEmpty(message = "The anime's name cannot be empty or null")
    @NotBlank(message = "The anime's name cannot be empty or null")
    private String name;
}
