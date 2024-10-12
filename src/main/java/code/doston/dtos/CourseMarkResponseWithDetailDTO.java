package code.doston.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class CourseMarkResponseWithDetailDTO {

    private Long id;
    private StudentResponseDTO student;
    private CourseResponseDTO course;
    private Integer mark;
    private LocalDateTime createdDate;
}
