package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final DateUtil dateUtil;
    private final AnimeService animeService;
    @GetMapping
    public ResponseEntity<List<Anime>> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    /* o endereço no Postman abaixo ficaria da seguinte forma:
     http://localhost:8080/animes/find?name=Hunter X Hunter */
    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
        return ResponseEntity.ok(animeService.findByName(name));
    }

    /*
    6) Por fim, você precisa informar ao lado do @RequestBody que você usou uma validação
    do Spring, que seria o item 4) dos comentários. Sendo assim, coloque a annotation
    chamada @Valid
     */
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
            return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
