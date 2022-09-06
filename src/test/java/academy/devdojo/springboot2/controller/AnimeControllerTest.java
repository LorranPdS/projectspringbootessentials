package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/*
2) Quando estamos criando testes unitários, temos algumas opções, e uma delas é você
adicionar a annotation @SpringBootTest, mas o problema em você usá-la é que você terá
o contexto do Spring sendo inicializado, ou seja, ele vai startar a aplicação para
iniciar os testes; como derrubamos o Docker, a aplicação falhará
 - porém, a opção que iremos usar será a annotation @ExtendWith e, através desse
 SpringExtension.class, nós estamos falando que estamos querendo utilizar o JUnit com
 o Spring
 */
@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    // 3) Basicamente usaremos duas anotações: @InjectMocks e @Mock

    /*
     4) Se você for testar a classe ABC, então você coloque a anotação @InjectMocks. O InjectMocks
     é a classe onde você injetará os mocks que você criará
     */
    @InjectMocks
    private AnimeController animeController;

    /*
     5) Já o @Mock você usa para todas as classes que serão usadas dentro do @InjectMocks
     que você colocou acima. Para nós não termos que ficar inicializando tudo, iremos fazer
     um mock do comportamento do AnimeService, ou seja, vamos definir um tipo de compoortamento
     para o que estiver com a annotation @Mock (dizer como ela se comportará). Ex.: se mockarmos
     o animeService.listAll, a classe de verdade não será executada mas sim o que definimos
     */
    @Mock
    private AnimeService animeServiceMock;

    /*
     8) A seguir precisamos definir o comportamento dos mocks. Como testaremos o método list(),
     então definiremos agora o comportamento do listAll()
     */
    @BeforeEach
    void setUp(){
        // 9) Ele criará, antes de rodar os testes, uma lista de animes
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        /*
         10) Aqui ele diz: quando alguém executar uma chamada para o animeService.listAll 'independente do que tiver como argumento', então você retornará o valor de cima
         */
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);
    }


    @Test
    @DisplayName("List returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSucessful(){
        // 12) Aqui pegaremos então o nome dele e guardaremos numa String
        String expectedName = AnimeCreator.createValidAnime().getName();

        // 11) Ele primeiro começou pela ação em vez de começar criando o cenário, o que é interessante mesmo
        Page<Anime> animePage = animeController.list(null).getBody();

        // 12) Agora faremos os assertions
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                . hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

}