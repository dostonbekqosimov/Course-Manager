package code.doston.repository;

import code.doston.entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Custom query methods with jpql

    // create


    @Query("from Course ")
    Page<Course> getAll(Pageable pageable);

    @Query("from Course where id = :id")
    Course getById(@Param("id") Long id);

    // get course by name
    @Query("from Course where name = :name")
    List<Course> getAllByName(String name);

    // get by price
    @Query("from Course where price = :price")
    Page<Course> getAllByPrice(@Param("price") BigDecimal price, Pageable pageable);

    // get by duration
    @Query("from Course where duration = :duration")
    List<Course> getByDuration(Integer duration);

    // get by price range
    @Query("from Course where price between :min and :max")
    Page<Course> getAllBetweenPrice(BigDecimal min, BigDecimal max, Pageable pageable);

    // get by date range
    @Query("from Course where createdDate between :fromDate and :toDate")
    List<Course> getAllBetweenDates(LocalDateTime fromDate, LocalDateTime toDate);

    // delete
    @Modifying
    @Transactional
    // nimadur qo'shish kerak to delete it
    @Query("delete from Course where id = :id")
    void deleteById(@Param("id") Long id);










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
