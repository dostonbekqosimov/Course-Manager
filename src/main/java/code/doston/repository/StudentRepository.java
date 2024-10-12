package code.doston.repository;

import code.doston.entity.Student;
import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByName(String name);

    List<Student> findBySurname(String surname);

    List<Student> findByLevel(Level level);

    List<Student> findByAge(Integer age);

    List<Student> findByGender(Gender gender);

//    List<Student> findByCreatedDate(LocalDate createdDate);

    List<Student> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    boolean existsByName(String name);


}
