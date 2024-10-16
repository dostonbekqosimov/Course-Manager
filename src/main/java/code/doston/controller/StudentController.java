package code.doston.controller;

import code.doston.dtos.StudentCreationDTO;
import code.doston.dtos.StudentResponseDTO;
import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import code.doston.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Should consider to change from path variable to query params

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    // Create a new student
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentCreationDTO studentCreationDTO) {

        return ResponseEntity.ok().body(studentService.createStudent(studentCreationDTO));
    }

    // Get a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.getStudent(id));
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok().body(studentService.getAllStudents());
    }

    // Update a student by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentCreationDTO studentCreationDTO) {
        return ResponseEntity.ok().body(studentService.updateStudent(id, studentCreationDTO));
    }

    // Delete a student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.deleteStudent(id));
    }

    // Get students by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<StudentResponseDTO>> getByName(@PathVariable String name) {
        List<StudentResponseDTO> students = studentService.getByName(name);
        return ResponseEntity.ok(students);
    }

    // Get students by surname
    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<StudentResponseDTO>> getBySurname(@PathVariable String surname) {
        List<StudentResponseDTO> students = studentService.getBySurname(surname);
        return ResponseEntity.ok(students);
    }

    // Get students by level
    @GetMapping("/level/{level}")
    public ResponseEntity<List<StudentResponseDTO>> getByLevel(@PathVariable Level level) {
        List<StudentResponseDTO> students = studentService.getByLevel(level);
        return ResponseEntity.ok(students);
    }

    // Get students by age
    @GetMapping("/age/{age}")
    public ResponseEntity<List<StudentResponseDTO>> getByAge(@PathVariable Integer age) {
        List<StudentResponseDTO> students = studentService.getByAge(age);
        return ResponseEntity.ok(students);
    }

    // Get students by gender
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<StudentResponseDTO>> getByGender(@PathVariable Gender gender) {
        List<StudentResponseDTO> students = studentService.getByGender(gender);
        return ResponseEntity.ok(students);
    }

    // Get students by given date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<StudentResponseDTO>> getByCreatedDate(@PathVariable String date) {
        LocalDate createdDate = LocalDate.parse(date); // Ensure the date format matches your input
        List<StudentResponseDTO> students = studentService.getByCreatedDate(createdDate);
        return ResponseEntity.ok(students);
    }

    //get students between given dates
    @GetMapping("/date/{startDate}/{endDate}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByDateRange(@PathVariable String startDate, @PathVariable String endDate) {
        LocalDate start = LocalDate.parse(startDate); // Ensure the date format matches your input
        LocalDate end = LocalDate.parse(endDate); // Ensure the date format matches your input
        List<StudentResponseDTO> students = studentService.getStudentsByCreatedDateBetween(start, end);
        return ResponseEntity.ok(students);
    }

}
