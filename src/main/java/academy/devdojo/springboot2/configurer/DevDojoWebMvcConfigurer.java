package academy.devdojo.springboot2.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/* WEBMVCCONFIGURER
1) Nesta aula nosso objetivo está em como trocar o padrão das páginas que retornamos.
Se você for notar, estamos retornando atualmente 20 elementos por padrão se a
requisição GET do listAll(). Sendo assim, vamos colocar por padrão de 20 para
5 elementos porque pode ser uma exigência do cliente. Claro, ainda temos a opção
de colocarmos na própria requisição, mas antes vamos cuidar do padrão. A requisição
será GET e será a mais simples:
http://localhost:8080/animes
 */

@Configuration // essa annotation será configurada globalmente no Spring
public class DevDojoWebMvcConfigurer implements WebMvcConfigurer {
    /*
    2) Pelo @Override abaixo, sabemos que é um método que já existe por padrão, e
    esse método padrão está no WebMvcConfigurer. Com o @Override que colocamos
    aqui, estamos sobrescrevendo (escrevendo em cima, antes) para que este método
    com @Override seja usado em vez do que está por padrão.
    Agora que sobrescrevemos o método com o @Override, podemos colocar uma
    configuração global.
    */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();

        /*
        3) Se você colocar o "page" abaixo como sendo "1", então quando o usuário
        chamar o método, ele abrirá a 2ª página primeiramente, então colocamos o
        valor "0" para que seja a 1ª página. O "size" será "5", assim virão os 5 primeiros
        valores em JSON para gente.
         */
        pageHandler.setFallbackPageable(PageRequest.of(0, 5));
        resolvers.add(pageHandler);
    }
}

/*
4) Caso queira sobrescrever para não usar o padrão, basta colocá-lo na requisição.
Por exemplo, se em vez de tamanho 5 quisermos retornar 8, faremos assim no GET:
http://localhost:8080/animes?size=8
 */
