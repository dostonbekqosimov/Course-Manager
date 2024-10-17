package code.doston.dtos.filterDTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CourseFilterDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;

}
