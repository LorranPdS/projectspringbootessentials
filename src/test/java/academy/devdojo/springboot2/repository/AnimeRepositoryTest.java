package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/* Spring Data JPA Test - parte 1
    1) Testes é um dos tópicos mais importantes e menos vistos por quem está aprendendo.
    Importante saber que, quando um recrutador de tecnologia recebe, numa vaga de emprego,
    a 1ª coisa que ele abre é essa parte de "testes" do projeto! Se você não cobre nem 80%
    dos seus métodos ou apenas se preocupa com o "Caminho feliz", eles nem veem o restante
    do que você vez e você já é eliminado de imediato, mesmo que você seja dev Senior!

    Portanto, mesmo que não peçam para você fazer teste unitário, faça porque esse é daquele
    tipo de coisa que não precisam te pedir pra você fazer, você PRECISA fazer!
 */

/*
    5) Por não ser um teste de integração, você não precisa deixar o projeto rodando, então
    você pode deixá-lo parado
 */

@DataJpaTest
@DisplayName("Tests for Anime Repository") // 4) Esse é o nome que aparecerá para a classe de teste no JUnit, mas se você não deixar, aparecerá apenas o nome da classe
class AnimeRepositoryTest {

    // 5) Não é indicado usarmos a anotação @Autowired diretamente nos campos, mas nos testes não tem problema, tanto é que não deu nenhum tipo de warning
    @Autowired
    private AnimeRepository animeRepository;

    /*
        8) Se você for ver na internet como nomear seus testes, veremos umas 8 convenções, mas se formos pensar,
        se há 8 convenções é porque não há convenção nenhuma, rsrsrs
        Então uma sugestão de como nomear as classes de testes seria:
        - o método que eu estou querendo testar -> save
        - seguido do que esse método precisa fazer -> PersistAnime
        - quando isso deve acontecer, ou seja, quando for bem sucedido -> WhenSuccessful
        Mas geralmente os 2 primeiros já é bom o bastante
     */
    @Test
    @DisplayName("Save creates anime when sucessful") // 7) Essa descrição aparecerá naquele bloco do JUnit que mostra quais testes rodaram
    void save_PersistAnime_WhenSuccessful(){

        // 10) Coloque o cursor dentro do método e use Ctrl + Shift + F10 para executá-lo
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // 9) Vamos usar esse assertions, mas tome muito cuidado com o import porque é bem parecido com um outro bem comum
        Assertions.assertThat(animeSaved).isNotNull(); // verifique que animeSaved não é nulo
        Assertions.assertThat(animeSaved.getId()).isNotNull(); // se tiver, foi que a base de dados criou o id
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName()); // você verificou que esse valor foi o mesmo enviado para a base
    }

    // 6) Vamos criar um Anime para gente que será salvo no h2
    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}