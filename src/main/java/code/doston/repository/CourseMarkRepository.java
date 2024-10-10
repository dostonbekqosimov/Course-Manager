package code.doston.repository;

import code.doston.entity.CourseMark;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CourseMarkRepository extends JpaRepository<CourseMark, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
