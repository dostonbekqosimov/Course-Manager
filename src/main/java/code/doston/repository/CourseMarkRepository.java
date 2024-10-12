package code.doston.repository;

import code.doston.entity.CourseMark;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface CourseMarkRepository extends JpaRepository<CourseMark, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

//    List<CourseMark> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<CourseMark> findByStudentIdAndCreatedDateBetween(Long studentId, LocalDateTime fromDate, LocalDateTime toDate);

    List<CourseMark> findAllByStudentIdOrderByCreatedDateAsc(Long studentId);

    List<CourseMark> findAllByStudentIdOrderByCreatedDateDesc(Long studentId);

    List<CourseMark> findAllByStudentId(Long studentId);

    List<CourseMark> findAllByStudentIdAndCourseIdOrderByCreatedDateAsc(Long studentId, Long courseId);

    List<CourseMark> findAllByStudentIdAndCourseIdOrderByCreatedDateDesc(Long studentId, Long courseId);

    List<CourseMark> findAllByCourseId(Long courseId);

    List<CourseMark> findByStudentIdOrderByCreatedDateDesc(Long studentId, Pageable pageable);

    boolean existsByStudentIdAndCourseIdAndCreatedDateBetween(Long student_id, Long course_id, LocalDateTime fromDate, LocalDateTime toDate);

    List<CourseMark> findTop3ByStudentIdOrderByCreatedDateDescMarkDesc(Long studentId);

    CourseMark findTop1ByStudentIdOrderByCreatedDateDesc(Long studentId);

    CourseMark findTop1ByStudentIdOrderByCreatedDateAsc(Long studentId);

    CourseMark findTop1ByStudentIdAndCourseIdOrderByCreatedDateAsc(Long studentId, Long courseId);

    CourseMark findTop1ByStudentIdAndCourseIdOrderByMarkDescCreatedDateDesc(Long studentId, Long courseId);

    List<CourseMark> findAllByStudentIdAndCourseId(Long studentId, Long courseId);

    CourseMark findTop1ByCourseIdOrderByMarkDesc(Long courseId);
}
