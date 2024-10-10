package code.doston.controller;


import code.doston.dtos.CourseMarkCreationDTO;
import code.doston.dtos.MarkDTO;
import code.doston.service.CourseMarkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/student-course-mark")
public class CourseMarkController {

    private final CourseMarkService courseMarkService;

    // create a new student course mark
    @PostMapping
    public ResponseEntity<?> createStudentCourseMark(@Valid @RequestBody CourseMarkCreationDTO courseMarkCreationDTO) {
        return ResponseEntity.ok().body(courseMarkService.createStudentCourseMark(courseMarkCreationDTO));
    }

    // update student course mark
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentCourseMark(@PathVariable Long id,
                                                     @Valid @RequestBody MarkDTO mark) {
        return ResponseEntity.ok().body(courseMarkService.updateStudentCourseMark(id, mark.getMark()));
    }

    // get student course mark
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentCourseMarkById(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseMarkService.getStudentCourseMarkById(id));
    }

    // get student course mark detail
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getStudentCourseMarkDetailById(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseMarkService.getStudentCourseMarkDetailById(id));
    }

    // delete student course mark
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentCourseMarkById(@PathVariable Long id) {
        courseMarkService.deleteStudentCourseMarkById(id);
        return ResponseEntity.noContent().build();
    }

    // get all student course marks
    @GetMapping
    public ResponseEntity<?> getAllStudentCourseMarks() {
        return ResponseEntity.ok().body(courseMarkService.getAllStudentCourseMarks());
    }
}





