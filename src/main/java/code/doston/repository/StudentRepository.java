package code.doston.repository;

import code.doston.entity.Course;
import code.doston.entity.Student;
import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Custom query methods

    // get by id
    @Query("from Student where id = :id")
    Student getWithId(Long id);

    // get all
    @Query("from Student")
    List<Student> getAllStudents();

    // get by name
    @Query("from Student where name = :name")
    List<Student> getAllStudentsByName(String name);

    // get by surname
    @Query("from Student where surname = :surname")
    List<Student> getAllStudentsBySurname(String surname);

    // get by level
    @Query("from Student where level = :level")
    Page<Student> getAllStudentsByLevel(Level level, Pageable pageable);

    // get by age
    @Query("from Student where age = :age")
    List<Student> getAllStudentsByAge(Integer age);

    // get by age
    @Query("from Student where gender = :gender")
    Page<Student> getAllStudentsByGender(Gender gender, Pageable pageable);

    // get by date between
    @Query("from Student where createdDate between :fromDate and :toDate")
    List<Student> getAllBetweenDates(LocalDateTime fromDate, LocalDateTime toDate);



    List<Student> findByName(String name);

    List<Student> findBySurname(String surname);

    List<Student> findByLevel(Level level);

    List<Student> findByAge(Integer age);

    List<Student> findByGender(Gender gender);

//    List<Student> findByCreatedDate(LocalDate createdDate);

    List<Student> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    boolean existsByName(String name);


}
