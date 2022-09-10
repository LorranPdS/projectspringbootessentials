package academy.devdojo.springboot2.requests;

import lombok.Builder;
import lombok.Data;

// ela nada mais é que uma classe DTO
@Data
@Builder
public class AnimePutRequestBody {
    private Long id;
    private String name;
}
