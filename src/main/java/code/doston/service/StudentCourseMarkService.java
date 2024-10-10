package code.doston.service;

import code.doston.dtos.StudentCourseMarkCreationDTO;
import code.doston.dtos.StudentCourseResponseDTO;
import code.doston.repository.StudentCourseMarkRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentCourseMarkService {

    private final StudentCourseMarkRepository studentCourseMarkRepository;
    private final CourseService courseService;
    private final StudentService studentService;
    private final ModelMapper modelMapper;

    public StudentCourseResponseDTO createStudentCourseMark(@Valid StudentCourseMarkCreationDTO studentCourseMarkCreationDTO) {

        return null;
    }
}
