package code.doston.dtos;

import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentCreationDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @NotNull(message = "Level cannot be null")
    private Level level;

    @NotNull(message = "Age cannot be null")
    @Min(value = 0, message = "Age must be a positive number")
    private Integer age;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;
}