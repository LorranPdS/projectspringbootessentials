package academy.devdojo.springboot2.repository.mapper;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-09T22:21:07-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class AnimeMapperImpl extends AnimeMapper {

    @Override
    public Anime toAnime(AnimePostRequestBody animePostRequestBody) {
        if ( animePostRequestBody == null ) {
            return null;
        }

        Anime.AnimeBuilder anime = Anime.builder();

        anime.name( animePostRequestBody.getName() );

        return anime.build();
    }

    @Override
    public Anime toAnime(AnimePutRequestBody animePutRequestBody) {
        if ( animePutRequestBody == null ) {
            return null;
        }

        Anime.AnimeBuilder anime = Anime.builder();

        anime.id( animePutRequestBody.getId() );
        anime.name( animePutRequestBody.getName() );

        return anime.build();
    }
}
