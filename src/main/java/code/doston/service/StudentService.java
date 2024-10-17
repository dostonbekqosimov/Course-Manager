package code.doston.service;


import code.doston.dtos.CustomFilterResponseDTO;
import code.doston.dtos.filterDTOs.StudentFilterDTO;
import code.doston.dtos.StudentCreationDTO;
import code.doston.dtos.StudentResponseDTO;
import code.doston.entity.Student;
import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import code.doston.exceptions.DataNotFoundException;
import code.doston.exceptions.DataExistsException;
import code.doston.repository.filterRepository.CustomStudentFilterRepository;
import code.doston.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


// Should consider to change from path variable to query params
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CustomStudentFilterRepository customStudentRepository;
    private final ModelMapper modelMapper;


    public StudentResponseDTO createStudent(StudentCreationDTO studentCreationDTO) {


        Student student = modelMapper.map(studentCreationDTO, Student.class);
        studentRepository.save(student);

        return modelMapper.map(student, StudentResponseDTO.class);


    }


    public StudentResponseDTO getStudent(Long id) {

        // Check if student exists
        idNotExists(id);

        return modelMapper.map(studentRepository.getWithId(id), StudentResponseDTO.class);
    }

    public List<StudentResponseDTO> getAllStudents() {

        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found");
        }

        // Map each Student to StudentResponseDTO using ModelMapper
        return students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());

//    List<Student> students = studentRepository.findAll();
//    List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();
//
//    for (Student student : students) {
//        StudentResponseDTO dto = modelMapper.map(student, StudentResponseDTO.class);
//        studentResponseDTOs.add(dto);
//    }
//
//    return studentResponseDTOs;
    }

    public List<StudentResponseDTO> getByName(String name) {

        // Check if the student exists
        List<Student> students = studentRepository.getAllStudentsByName(name);
        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found with name: " + name);
        }
        return students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getBySurname(String surname) {

        List<Student> students = studentRepository.getAllStudentsBySurname(surname);
        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found with surname: " + surname);
        }
        return students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PageImpl<StudentResponseDTO> getByLevel(Level level, Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Student> students = studentRepository.getAllStudentsByLevel(level, pageRequest);

        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found with level: " + level);
        }
        List<StudentResponseDTO> studentResponseDTOS = students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(studentResponseDTOS, pageRequest, students.getTotalElements());

    }

    public List<StudentResponseDTO> getByAge(int age) {

        List<Student> students = studentRepository.getAllStudentsByAge(age);
        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found with age: " + age);
        }
        return students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PageImpl<StudentResponseDTO> getByGender(Gender gender, Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());


        Page<Student> students = studentRepository.getAllStudentsByGender(gender, pageRequest);
        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found with gender: " + gender);
        }
        List<StudentResponseDTO> studentList = students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(studentList, pageRequest, students.getTotalElements());
    }

    public List<StudentResponseDTO> getByCreatedDate(LocalDate createdDate) {

        LocalDateTime fromDate = LocalDateTime.of(createdDate, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(createdDate, LocalTime.MAX);

        List<Student> students = studentRepository.getAllBetweenDates(fromDate, toDate);
        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found with createdDate: " + createdDate);
        }
        return students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentsByCreatedDateBetween(LocalDate startDate, LocalDate endDate) {


        LocalDateTime fromDate = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(endDate, LocalTime.MAX);
        List<Student> students = studentRepository.getAllBetweenDates(fromDate, toDate);

        if (students.isEmpty()) {
            throw new DataNotFoundException("No student found with createdDate between: " + startDate + " and " + endDate);
        }
        return students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());
    }

    public boolean updateStudent(Long studentId, StudentCreationDTO studentCreationDTO) {

        // Check if the student exists
        idNotExists(studentId);

        // Get the existing student from the database
        Student student = studentRepository.findById(studentId).get();

        // Use ModelMapper to map non-null fields from studentCreationDTO to the existing student
        modelMapper.map(studentCreationDTO, student);

        // Save the updated student entity
        studentRepository.save(student);
        return true;
    }

    public Student getStudentEntityById(Long id) {

        // Check if the student exists
        idNotExists(id);
        return studentRepository.findById(id).get();
    }


    public String deleteStudent(Long id) {

        // Check if the student exists
        idNotExists(id);

        // Delete the student from the database
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

    public PageImpl<StudentResponseDTO> filter(StudentFilterDTO filterDTO, int page, int size) {

        CustomFilterResponseDTO<Student> result = customStudentRepository.filter(filterDTO, page, size);
        List<StudentResponseDTO> studentResponseDTOS = result.getResult().stream().map(student -> modelMapper.map(student, StudentResponseDTO.class)).toList();


        return new PageImpl<>(studentResponseDTOS, PageRequest.of(page, size), result.getTotalCount());
    }


    public void idNotExists(Long id) {
        boolean isExist = studentRepository.existsById(id);
        if (!isExist) {
            throw new DataExistsException("Student not found with id: ", id);
        }
    }

//    public void checkEnumValue(StudentCreationDTO studentCreationDTO) {
//        if (!EnumSet.allOf(Gender.class).contains(studentCreationDTO.getGender())) {
//            throw new EnumValidationException("Invalid gender value: ");
//        }
//        if (!EnumSet.allOf(Level.class).contains(studentCreationDTO.getLevel())) {
//            throw new EnumValidationException("Invalid level value: ");
//        }
//    }
}
