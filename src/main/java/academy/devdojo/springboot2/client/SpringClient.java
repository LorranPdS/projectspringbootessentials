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

        Anime kingdom = Anime.builder().name("Kingdom").build();
        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
        log.info("Saved anime {}", kingdomSaved);

        Anime samuraiChamploo = Anime.builder().name("samuraiChamploo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
                        HttpMethod.POST,
                        new HttpEntity<>(samuraiChamploo, createJsonHeader()),
                        Anime.class);
        log.info("Saved anime {}", samuraiChamplooSaved);

        /* RestTemplate usando PUT e DELETE
        1) Se você ver, o RestTemplate() até tem o "put" e o "delete" mas ele não mostra retorno algum, então você não sabe
        o que aconteceu com aquela requisição e é interessante que retornemos pelo menos o status HTTP como retorno já que eu
        estou retornando uma requisição para um servidor, e provavelmente remoto, e eu quero saber o que aconteceu naquele servidor.
        Então vamos usar o "exchange" para o PUT e para o DELETE.
        */
        Anime animeToBeUpdated = samuraiChamplooSaved.getBody();
        animeToBeUpdated.setName("Samurai Champloo 2");
        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);
        /*
        2) Se você for ver lá no nosso AnimeController, tanto o PUT quanto o DELETE retornam um Void, então por esse motivo que colocamos
        o Void.class no exchange.
         */
        log.info(samuraiChamplooUpdated);

        /*
        3) Veja que o DELETE abaixo é muito parecido com o UPDATE de cima, mas basicamente colocamos ele para encontrar um id a ser
        removido e mudamos o método HTTP dele basicamente.

        4) Lembrando que usamos Void para o DELETE e para o PUT, mas usamos o retorno do ResponseEntity para ter um valor de retorno da
        requisição (nesse caso seria o 204, lembrando que a família do 200 significa "sucesso" e poderia até mesmo fazer um tipo de
        tratamento se retornasse diferente de 200)
         */
        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());
        log.info(samuraiChamplooDeleted);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
