package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
@Log4j2 // só colocamos o log4j2 para ver se o dado está correto mesmo
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when sucessful")
    void save_PersistAnime_WhenSuccessful(){

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when sucessful")
    void save_UpdatesAnime_WhenSuccessful(){

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // 1) Após, salvo, então vamos setar um outro nome para atualizar o conteúdo
        animeSaved.setName("Overlord");

        // 2) Atualizamos o que foi setado acima na base de dados e a recuperamos numa variável local para fazer as asserções
        Anime animeUpdated = this.animeRepository.save(animeSaved);
//        log.info(animeUpdated.getName()); => já podemos remover esse cara

        // Asserção 1: garantir que o retorno do que foi salvo acima não está nulo
        Assertions.assertThat(animeUpdated).isNotNull();

        // Asserção 2: garantir que o id retornado foi gerado na h2
        Assertions.assertThat(animeUpdated.getId()).isNotNull();

        // Asserção 3: verificar se o nome com o qual foi setado é igual ao que está na h2 (Overlord)
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSuccessful(){

        // 1) Aqui o anime foi criado no objeto
        Anime animeToBeSaved = createAnime();

        // 2) Abaixo ele será salvo no h2
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // 3) Precisamos receber o retorno para fazer a asserção mas veja que o retorno do delete é void, então faremos conforme o próximo item
        this.animeRepository.delete(animeSaved);

        // 4) Desta forma que testaremos mesmo o delete sendo void: verificando se o id no anime criado num primeiro momento ainda existe no h2, então se ele não existe, significa que foi removido
        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        // Asserção: se o retorno da busca vier vazio, sabemos que foi sim removido do h2
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of anime when sucessful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){

        // 1) Aqui o anime foi criado no objeto
        Anime animeToBeSaved = createAnime();

        // 2) Abaixo ele será salvo no h2
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // 3) A seguir vou fazer a busca do anime pelo nome para ver se o findByName está funcionando e o mesmo me retornará uma lista de animes
        List<Anime> animes = this.animeRepository.findByName(animeSaved.getName());

        // Asserção 1: verificar se a lista acima não veio vazia. Desta forma sabemos que o nome foi buscado
        Assertions.assertThat(animes).isNotEmpty();

        // Asserção 2: verificar se na lista de animes retornada há o anime que foi salvo no h2 (item 2)
        Assertions.assertThat(animes).contains(animeSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){

        // 1) Vamos fazer uma busca por um nome que sabemos não existir e testar a reação dele. No nosso caso, ele virá null
        List<Anime> animes = this.animeRepository.findByName("This name isn't exists");

        // Asserção: verificar se realmente o nome do anime que coloquei virá vazio na lista, já que coloquei um nome que não existe
        Assertions.assertThat(animes).isEmpty(); // Obs.: aqui, o '.isEmpty()' é diferente de '.isNull()'
    }

    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}