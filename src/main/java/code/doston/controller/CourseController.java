package code.doston.controller;

import code.doston.dtos.CourseCreationDTO;
import code.doston.dtos.CourseResponseDTO;
import code.doston.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Create a new course
    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseCreationDTO courseCreationDTO) {
        return ResponseEntity.ok().body(courseService.createCourse(courseCreationDTO));

    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.getCourse(id));
    }

}
