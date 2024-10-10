package code.doston.dtos;

import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentCreationDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Level cannot be null")
    private Level level;

    @NotNull(message = "Age cannot be null")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender cannot be null")
    private Gender gender;
}