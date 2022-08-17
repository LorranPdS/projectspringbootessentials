package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

//    /*
//    4) Então quando temos uma situação em que precisaremos checar se as coisas que
//    estamos fazendo precisará fazer um rollback caso estoure uma exceção, você
//    precisará usar a anotar o seu método com o @Transactional do pacote spring.framework e,
//    só de fazer essa anotação, o Spring não comitará sua transação enquanto o
//    método não for finalizado
//     */
//    @Transactional
//    public Anime save(AnimePostRequestBody animePostRequestBody){
//        // return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
//
//        /* 1) ANTES DE TUDO: dê o comando "show table status" na sua base de dados: se
//        a coluna Engine tiver como InnoDB, significa que sua base de dados está
//        apta a trabalhar com transações
//
//        2) Vamos simular logo abaixo uma situação em que, depois de cadastrado um dado,
//        ele estoura um erro. Caso isso acontecesse realmente, seria necessário ocorrer
//        um rollback na base e assim não ser salvo o dado. Para isso que funcionará
//        a anotação @Transactional acima do método.
//         */
//
//        Anime anime = animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
//        if(true){
//            throw new RuntimeException("Simulação Bad Code");
//        }
//        return anime; // 3) então o correto é que não seja salvo, já que há uma exceção acima
//    }

    /*
    5) Um PORÉM muito importante envolvendo o @Transactional e exceções checadas (Exception
    por exemplo) é que por padrão ele não faz rollback para exceções do tipo check,
    então é necessário que você avise para mais qual tipo de exceção você
    vai querer rollback (no caso pelo rollbackFor). Compare o método de baixo
    com o método de cima para entender as duas situações.
     */
    @Transactional(rollbackFor = Exception.class)
    public Anime save(AnimePostRequestBody animePostRequestBody) throws Exception{
        Anime save = animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
        if(true){
            throw new Exception("Testando com Checked Exception");
        }
        return save;
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody){
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());

        animeRepository.save(anime);
    }
}
