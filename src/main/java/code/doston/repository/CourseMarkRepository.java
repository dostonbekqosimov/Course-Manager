package code.doston.repository;

import code.doston.entity.CourseMark;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface CourseMarkRepository extends JpaRepository<CourseMark, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    // Custom query methods

    @Query("from CourseMark as cm where cm.student.id =:studentId and cm.createdDate between :fromDate and :toDate ")
    Page<CourseMark> fetchByStudentId(@Param("studentId") Long studentId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    @Query("from CourseMark cm where cm.course.id = :courseId")
    Page<CourseMark> fetchAllByCourseId(@Param("courseId") Long courseId, Pageable pageable);

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
