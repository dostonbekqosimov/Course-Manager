package code.doston.controller;

import code.doston.dtos.CourseCreationDTO;
import code.doston.dtos.CourseResponseDTO;
import code.doston.dtos.filterDTOs.CourseFilterDTO;
import code.doston.dtos.filterDTOs.StudentFilterDTO;
import code.doston.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public ResponseEntity<PageImpl<CourseResponseDTO>> getAllCourses(@RequestParam(value = "page", required = false) Integer page,
                                                                     @RequestParam(value = "size", required = false) Integer size) {
        return ResponseEntity.ok().body(courseService.getAllCourses(page - 1, size));
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
    @GetMapping("/price/{price}")
    public ResponseEntity<PageImpl<CourseResponseDTO>> getByPrice(@PathVariable(value = "price") BigDecimal price,
                                                                  @RequestParam(value = "page", required = false) Integer page,
                                                                  @RequestParam(value = "size", required = false) Integer size) {
        return ResponseEntity.ok().body(courseService.getByPrice(price, page - 1, size));
    }

    // Get course by duration
    @GetMapping("/duration")
    public ResponseEntity<List<CourseResponseDTO>> getByDuration(@RequestParam(value = "duration") Integer duration) {
        return ResponseEntity.ok().body(courseService.getByDuration(duration));
    }

    // Get course list between two prices
    @GetMapping("/price-range")
    public ResponseEntity<PageImpl<CourseResponseDTO>> getByPriceRange(@RequestParam(value = "min") BigDecimal min,
                                                                       @RequestParam(value = "max") BigDecimal max,
                                                                       @RequestParam(value = "page", required = false) Integer page,
                                                                       @RequestParam(value = "size", required = false) Integer size) {
        return ResponseEntity.ok().body(courseService.getByPriceRange(min, max, page, size));
    }

    // Get course list between two created dates
    @GetMapping("/date-range")
    public ResponseEntity<List<CourseResponseDTO>> getStudentsByDateRange(@RequestParam(value = "startDate") String startDate,
                                                                          @RequestParam(value = "endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate); // Ensure the date format matches your input
        LocalDate end = LocalDate.parse(endDate); // Ensure the date format matches your input

        return ResponseEntity.ok().body(courseService.getCoursesByCreatedDateBetween(start, end));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<CourseResponseDTO>> getFilteredStudentList(@RequestBody CourseFilterDTO filter,
                                                       @RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        return ResponseEntity.ok(courseService.filter(filter, page - 1, size));
    }


}
