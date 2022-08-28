package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/* RestTemplate
    1) Quando vamos desenvolvendo nossas aplicações, em algum momento vamos precisar
    fazer requisições para outros serviços de alguma URL externa mas você não quer fazer
    essa requisição manualmente. Sendo assim, o Spring oferece para você uma biblioteca
    que vai fazer essa chamada para você e,automaticamente, o mapeamento dos dados para
    sua classe. Então vamos ver como esse cara funciona, que é o RestTemplate usando o
    "getForObject" e o "getForEntity"
 */
@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        /*
        2) No nosso exemplo abaixo, nós vamos fazer uma requisição para o nosso próprio serviço,
        por isso colocamos a porta 8080, mas no geral você colocará o serviço da API externa que
        você vai querer enviar
         */
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 6);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 6);
        log.info(object);
    }
}
