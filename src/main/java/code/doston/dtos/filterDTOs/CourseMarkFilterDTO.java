package code.doston.dtos.filterDTOs;



import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseMarkFilterDTO {
    private Long studentId;
    private Long courseId;
    private Integer markFrom;
    private Integer markTo;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private String studentName;
    private String courseName;
}
