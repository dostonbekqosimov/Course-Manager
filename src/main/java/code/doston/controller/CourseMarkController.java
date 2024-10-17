package code.doston.controller;


import code.doston.dtos.*;
import code.doston.dtos.filterDTOs.CourseMarkFilterDTO;
import code.doston.entity.CourseMark;
import code.doston.service.CourseMarkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


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

    // get student's marks in given date
    @GetMapping("/student/{studentId}/date")
    public ResponseEntity<?> getStudentCourseMarksByCreatedDate(
            @PathVariable Long studentId,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size) {

        LocalDate givenDate = LocalDate.parse(date);
        return ResponseEntity.ok().body(courseMarkService.getStudentCourseMarksByCreatedDate(studentId, givenDate, page - 1, size));
    }

    // get student's marks in given date range
    @GetMapping("/student/{studentId}/date-range")
    public ResponseEntity<?> getStudentCourseMarksByCreatedDateRange(
            @PathVariable Long studentId,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok().body(courseMarkService.getStudentCourseMarksByCreatedDateRange(studentId, start, end));
    }

    // get student's marks sorted by date
    @GetMapping("/student/{studentId}/marks/sorted")
    public ResponseEntity<?> getStudentCourseMarksSortedByDate(
            @PathVariable Long studentId,
            @RequestParam(value = "sort", defaultValue = "desc") String sort) {

        return ResponseEntity.ok().body(courseMarkService.getStudentCourseMarksSortedByDate(studentId, sort));
    }

    // get student's marks for a given course, sorted by creation date (ascending or descending).
    @GetMapping("/student/{studentId}/course/{courseId}/marks/sorted")
    public ResponseEntity<?> getSortedMarksByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestParam(value = "sort", defaultValue = "desc") String sort) {

        return ResponseEntity.ok().body(courseMarkService.getSortedMarksByStudentAndCourse(studentId, courseId, sort));
    }

    // get student's last mark
    @GetMapping("/student/{studentId}/last-mark")
    public ResponseEntity<?> getStudentLastMark(@PathVariable Long studentId) {
        return ResponseEntity.ok().body(courseMarkService.getStudentLastMark(studentId));
    }

    // get student's last 3 biggest marks
    @GetMapping("/student/{studentId}/last-three-biggest-marks")
    public ResponseEntity<?> getStudentLastThreeBiggestMarks(@PathVariable Long studentId) {
        return ResponseEntity.ok().body(courseMarkService.getStudentLastThreeBiggestMarks(studentId));
    }

    // get student's first mark
    @GetMapping("/student/{studentId}/first-mark")
    public ResponseEntity<?> getStudentFirstMark(@PathVariable Long studentId) {
        return ResponseEntity.ok().body(courseMarkService.getStudentFirstMark(studentId));
    }

    // get student's first mark from given course
    @GetMapping("/student/{studentId}/first-mark/course/{courseId}")
    public ResponseEntity<?> getStudentFirstMarkByCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok().body(courseMarkService.getStudentFirstMarkByCourse(studentId, courseId));
    }

    // get student's the biggest mark from given course
    @GetMapping("/student/{studentId}/biggest-mark/course/{courseId}")
    public ResponseEntity<?> getStudentBiggestMarkByCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok().body(courseMarkService.getStudentBiggestMarkByCourse(studentId, courseId));
    }

    // Get student's average marks
    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<?> getStudentAverageMarks(@PathVariable Long studentId) {
        Double averageMarks = courseMarkService.getStudentAverageMarks(studentId);
        return ResponseEntity.ok().body(averageMarks);
    }

    // Get student's average marks by course
    @GetMapping("/student/{studentId}/course/{courseId}/average")
    public ResponseEntity<?> getStudentAverageMarksByCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        Double averageMarks = courseMarkService.getStudentAverageMarksByCourse(studentId, courseId);
        return ResponseEntity.ok().body(averageMarks);
    }

    // Count student's marks greater than a given value
    @GetMapping("/student/{studentId}/marks/count-greater-than/{threshold}")
    public ResponseEntity<?> countMarksGreaterThan(@PathVariable Long studentId, @PathVariable Double threshold) {
        Long count = courseMarkService.countMarksGreaterThan(studentId, threshold);
        return ResponseEntity.ok().body(count);
    }

    // get the list of course_mark by courseId
    @GetMapping("/course/{courseId}")
    public ResponseEntity<PageImpl<MarkResponseWithDetailDTO>> getAllByCourseId(@PathVariable Long courseId,
                                                                                @RequestParam(value = "page") int page,
                                                                                @RequestParam(value = "size") int size) {

        return ResponseEntity.ok().body(courseMarkService.getAllByCourseId(courseId, page, size));

    }

    // Get the highest mark for a given course
    @GetMapping("/course/{courseId}/highest-mark")
    public ResponseEntity<?> getHighestMarkByCourse(@PathVariable Long courseId) {
        Double highestMark = courseMarkService.getHighestMarkByCourse(courseId);
        return ResponseEntity.ok().body(highestMark);
    }

    // Get the average mark for a given course
    @GetMapping("/course/{courseId}/average")
    public ResponseEntity<?> getAverageMarkByCourse(@PathVariable Long courseId) {
        Double averageMark = courseMarkService.getAverageMarkByCourse(courseId);
        return ResponseEntity.ok().body(averageMark);
    }

    // Count marks for a given course
    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<?> countMarksByCourse(@PathVariable Long courseId) {
        Long count = courseMarkService.countMarksByCourse(courseId);
        return ResponseEntity.ok().body(count);
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<MarkResponseWithDetailDTO>> filterCourseMarks(
            @RequestBody CourseMarkFilterDTO filterDTO,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {


        return ResponseEntity.ok().body(courseMarkService.filterCourseMarks(filterDTO, page, size));
    }


}





