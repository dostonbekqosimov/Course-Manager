package code.doston.controller;


import code.doston.dtos.StudentCourseMarkCreationDTO;
import code.doston.service.StudentCourseMarkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/student-course-mark")
public class StudentCourseMarkController {


    private final StudentCourseMarkService studentCourseMarkService;

    @PostMapping
    public ResponseEntity<?> createStudentCourseMark(@Valid @RequestBody StudentCourseMarkCreationDTO studentCourseMarkCreationDTO) {
        return ResponseEntity.ok().body(studentCourseMarkService.createStudentCourseMark(studentCourseMarkCreationDTO));

    }
}
