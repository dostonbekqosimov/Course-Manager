package code.doston.dtos;

import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import lombok.Data;

@Data
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private Level level;
    private Integer age;
    private Gender gender;

}
