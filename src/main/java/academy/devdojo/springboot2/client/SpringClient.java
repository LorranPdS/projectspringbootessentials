package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/*
Obs.: essa classe foi criada apenas para executarmos uma vez só para vermos como
aparecerá no console, ou seja, não ficará a classe main principal do Spring rodando
enquanto essa vai executando porque isso aqui é apenas para testes.
 */
@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 6);
        log.info(entity);

        /*
        3) Imagine que você queira fazer um mapeamento automático porém, agora os dados estão dentro
        de uma lista ou um array. Quando você usa o "getForObject", você poderia chegar nesse resultado
        fazendo da forma logo abaixo. Da forma abaixo você terá um array de objetos do tipo "Anime[]" e você
        terá listado nos seus logs todos os animes da sua base, porém trabalhar com arrays não é
        algo tão atual, então faremos na variável "exchange" usando uma lista com o uso do "exchange" em vez
        do "getForObject"
         */
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        /*
        4) Então nós utilizaremos superTypeToken (ou algo do gênero) e ele fará essa conversão de Array para List, e
        pelo RestTemplate() isso é possível usando o método "exchange", então trocaremos o "getForObject" pelo "exchange".
        O exchange não tem getter ou setter, então você precisa escrever exatamente o método que você quer (no nosso caso o GET)
        se estivéssemos usando um POST, usaríamos um requestEntity mas por ser um GET vamos deixar como "null" e
        por fim faremos o que queríamos, que é retornar uma "lista automática" de Animes em vez de "array"
         */
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}); // 5) teremos essa chave vazia por ser uma classe abstrata, e você verá se entrar no método
        log.info(exchange.getBody());
    }
}
