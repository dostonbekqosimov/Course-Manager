package code.doston.repository;

import code.doston.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);

    List<Course> findByName(String name);

    boolean existsByPrice(BigDecimal price);

    List<Course> findByPrice(BigDecimal price);

    boolean existsByDuration(Integer duration);

    List<Course> findByDuration(Integer duration);

    boolean existsByPriceBetween(BigDecimal min, BigDecimal max);

    List<Course> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Course> findByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

}
