package code.doston.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseMarkCreationDTO {

    private Long id;

    @NotNull(message = "Student ID must not be null.")
    @Positive(message = "Student ID must be a positive number.")
    private Long studentId;

    @NotNull(message = "Course ID must not be null.")
    @Positive(message = "Course ID must be a positive number.")
    private Long courseId;

    @NotNull(message = "Mark must not be null.")
    @Min(value = 0, message = "Mark must be at least 0.")
    @Max(value = 100, message = "Mark must not exceed 100.")
    private Integer mark;
    private LocalDate createdDate;
}
