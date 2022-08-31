package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/*
    3) Como vamos testar o repository, vamos precisar criar a classe lá no
    diretório test, porém se você colocar o cursor em cima do "AnimeRepository" e
    digitar o atalho Alt + Enter, você já criará um teste para essa interface lá
    no diretório test em vez de fazer manualmente
 */
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByName(String name);
}
