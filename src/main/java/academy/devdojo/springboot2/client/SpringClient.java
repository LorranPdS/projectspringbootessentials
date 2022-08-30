package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        /*
        Basicamente a diferença entre o "getForEntity" e o "getForObject" dos dois retornos abaixo é que
        o primeiro (entity) retornará o objeto dentro do response, enquanto o segundo (object) retornará o próprio objeto.
        */
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 6);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 6);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){});
        log.info(exchange.getBody());

        /* Fazendo o RestTemplate POST
        1) Com o RestTemplate usando o método POST funciona de uma forma bem parecida com o que houve usando o RestTemplate do GET,
        como veremos ocorrer com as variáveis kingdomSaved e samudaiChamplooSaved, uma usando o "postForObject" e outra usando
        o "exchange"
         */
        Anime kingdom = Anime.builder().name("Kingdom").build();
        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
        log.info("Saved anime {}", kingdomSaved);

        /*
        2) Uma pequena diferença do "exchange POST" abaixo para o "exchange GET" acima é que acima há um argumento null porque o
        nosso RequestEntity não usa uma requisição GET para argumentação, mas como no POST nós precisamos enviar um objeto, nós
        passamos o objeto "samuraiChamploo" dentro do HttpEntity.
        Portanto, uma coisa boa em se usar o "exchange" é que você pode enviar headers HTTP dentro do HttpEntity.
         */
        Anime samuraiChamploo = Anime.builder().name("samuraiChamploo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
                        HttpMethod.POST,
                        new HttpEntity<>(samuraiChamploo, createJsonHeader()),
                        Anime.class);
        log.info("Saved anime {}", samuraiChamplooSaved);
    }

    /*
    3) Através desse método abaixo nós iremos enviar um header dizendo que o ContentType dessa
    requisição é um application/json
     */
    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
