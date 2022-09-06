package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;
/*
6) Dentro dessa classe AnimeCreator nós iremos criar todos os objetos que vamos
utilizar nas nossas classes de teste
 */
public class AnimeCreator {
    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }

    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .id(1l)
                .build();
    }

    public static Anime createValidUpdatedAnime(){
        return Anime.builder()
                .name("Hajime no Ippo com update")
                .id(1l)
                .build();
    }
}
