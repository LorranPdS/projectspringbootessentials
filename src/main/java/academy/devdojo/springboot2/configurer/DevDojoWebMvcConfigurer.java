package academy.devdojo.springboot2.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class DevDojoWebMvcConfigurer implements WebMvcConfigurer {
    /* USO DO SORTING e do LOG SQL
    1) por padrão, quando adicionamos o Pageable, já vem o sort junto, Para usarmos
    o "sort" (ordenação), você colocará qual campo você vai querer que sirva
    como critério de ordenação e se vai querer que seja em ordem crescente
    ou descrescente. Vejamos abaixo algumas opções da mesma requisição GET:
        a) pelo "name"
        http://localhost:8080/animes?size=8&sort=name

        b) pelo "id"
        http://localhost:8080/animes?size=8&sort=id

        c) pelo name em ordem descrescente (é uma vírgula após o name)
        http://localhost:8080/animes?size=8&sort=name,desc

        d) pelo name em ordem crescente
        http://localhost:8080/animes?size=8&sort=name,asc

     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        pageHandler.setFallbackPageable(PageRequest.of(0, 5));
        resolvers.add(pageHandler);
    }
}
