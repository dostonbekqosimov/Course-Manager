package code.doston.controller;

import code.doston.dtos.CourseCreationDTO;
import code.doston.dtos.CourseResponseDTO;
import code.doston.dtos.StudentResponseDTO;
import code.doston.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.getCourse(id));
    }

    // Delete a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.deleteCourse(id));
    }

    // Update a course by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseCreationDTO courseCreationDTO) {
        return ResponseEntity.ok().body(courseService.updateCourse(id, courseCreationDTO));
    }

    // Get courses by name
    @GetMapping("/name")
    public ResponseEntity<List<CourseResponseDTO>> getByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok().body(courseService.getCoursesByName(name));
    }

    // Get course by price
    @GetMapping("/price")
    public ResponseEntity<List<CourseResponseDTO>> getByPrice(@RequestParam(value = "price") BigDecimal price) {
        return ResponseEntity.ok().body(courseService.getByPrice(price));
    }

    // Get course by duration
    @GetMapping("/duration")
    public ResponseEntity<List<CourseResponseDTO>> getByDuration(@RequestParam(value = "duration") Integer duration) {
        return ResponseEntity.ok().body(courseService.getByDuration(duration));
    }

    // Get course list between two prices
    @GetMapping("/price-range")
    public ResponseEntity<List<CourseResponseDTO>> getByPriceRange(@RequestParam(value = "min") BigDecimal min, @RequestParam(value = "max") BigDecimal max) {
        return ResponseEntity.ok().body(courseService.getByPriceRange(min, max));
    }

    // Get course list between two created dates
    @GetMapping("/date-range")
    public ResponseEntity<List<CourseResponseDTO>> getStudentsByDateRange(@RequestParam(value = "startDate") String startDate,
                                                                          @RequestParam(value = "endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate); // Ensure the date format matches your input
        LocalDate end = LocalDate.parse(endDate); // Ensure the date format matches your input

        return ResponseEntity.ok().body(courseService.getCoursesByCreatedDateBetween(start, end));
    }





}
