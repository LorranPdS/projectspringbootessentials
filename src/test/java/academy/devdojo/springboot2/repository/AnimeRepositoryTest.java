package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
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
        animeSaved.setName("Overlord");

        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSuccessful(){

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);
        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of anime when sucessful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        List<Anime> animes = this.animeRepository.findByName(animeSaved.getName());

        /*
        1) O aviso 'warning' é quando você tem um mesmo objeto com diferentes tipos de exceções.
        Então o que você poderá fazer é checar se ele não é vazio por exemplo e em seguida verifica
        se ele tem esse anime que foi salvo
        */
        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved);
        Assertions.assertThat(animes).contains(animeSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){

        List<Anime> animes = this.animeRepository.findByName("This name isn't exists");

        Assertions.assertThat(animes).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty(){

        // 3) Vamos instanciar um anime, pois a instância é vazia e esse será nosso cenário
        Anime anime = new Anime();

        // 4.a) Essa é uma forma
//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        // 4.b) Essa é outra forma
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("The anime's name cannot be empty or null");
    }

    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}