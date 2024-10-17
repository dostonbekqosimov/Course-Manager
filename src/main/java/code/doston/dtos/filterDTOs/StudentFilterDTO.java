package code.doston.dtos.filterDTOs;


import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudentFilterDTO {

    private String name;
    private String surname;
    private Integer age;
    private Level level;
    private Gender gender;

    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;


}
