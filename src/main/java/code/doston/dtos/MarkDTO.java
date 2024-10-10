package code.doston.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkDTO {
    @NotNull(message = "Mark cannot be null")
    private Integer mark;


}

