package code.doston.dtos;

import lombok.Data;

@Data
public class CourseMarkResponseDTO {

    private Long id;
    private Long studentId;
    private Long courseId;
    private Integer mark;
}
