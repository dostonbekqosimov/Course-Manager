package code.doston.controller;

import code.doston.dtos.filterDTOs.StudentFilterDTO;
import code.doston.dtos.StudentCreationDTO;
import code.doston.dtos.StudentResponseDTO;
import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import code.doston.exceptions.EnumValidationException;
import code.doston.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

// Should consider to change from path variable to query params

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;


    @PostMapping("/filter")
    public ResponseEntity<PageImpl> getFilteredStudentList(@RequestBody StudentFilterDTO filter,
                                                           @RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size) {
        return ResponseEntity.ok(studentService.filter(filter, page - 1, size));
    }

    // Create a new student
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentCreationDTO studentCreationDTO) {

        // Check if the enum values are valid(bu service da ishlamadi, buyerdayam ishlamadi)
        checkEnumValue(studentCreationDTO);

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
    public ResponseEntity<PageImpl<StudentResponseDTO>> getByLevel(@PathVariable Level level,
                                                                   @RequestParam("page") Integer page,
                                                                   @RequestParam("size") Integer size) {

        return ResponseEntity.ok().body(studentService.getByLevel(level, page - 1, size));
    }

    // Get students by age
    @GetMapping("/age/{age}")
    public ResponseEntity<List<StudentResponseDTO>> getByAge(@PathVariable Integer age) {
        List<StudentResponseDTO> students = studentService.getByAge(age);
        return ResponseEntity.ok(students);
    }

    // Get students by gender
    @GetMapping("/gender/{gender}")
    public ResponseEntity<PageImpl<StudentResponseDTO>> getByGender(@PathVariable Gender gender,
                                                                    @RequestParam("page") Integer page,
                                                                    @RequestParam("size") Integer size) {
        return ResponseEntity.ok().body(studentService.getByGender(gender, page, size));
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

    public void checkEnumValue(StudentCreationDTO studentCreationDTO) {
        if (!EnumSet.allOf(Gender.class).contains(studentCreationDTO.getGender())) {
            throw new EnumValidationException("Invalid gender value: ");
        }
        if (!EnumSet.allOf(Level.class).contains(studentCreationDTO.getLevel())) {
            throw new EnumValidationException("Invalid level value: ");
        }
    }

}
