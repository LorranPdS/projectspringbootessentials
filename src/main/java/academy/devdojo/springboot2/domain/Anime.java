package academy.devdojo.springboot2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    3) Como não estamos usando a class Anime dentro dos controllers, então não usaremos
     a annotation aqui, mas vamos usar a annotation do spring-validator dentro de
     alguma que esteja usando dentro dos controllers, que no caso então seria no
     requests (nosso DTO)
     */
    private String name;
}
