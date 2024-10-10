package code.doston.dtos;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CourseResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer duration;
//    private LocalDate createdDate;
}
