package code.doston.repository.filterRepository;

import code.doston.dtos.filterDTOs.CourseMarkFilterDTO;
import code.doston.dtos.CustomFilterResponseDTO;
import code.doston.entity.CourseMark;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomCourseMarkFilterRepository {

    private final EntityManager entityManager;

    public CustomFilterResponseDTO<CourseMark> filter(CourseMarkFilterDTO filter, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder("SELECT cm FROM CourseMark cm JOIN cm.student s JOIN cm.course c WHERE 1=1 ");
        StringBuilder countBuilder = new StringBuilder("SELECT COUNT(*) FROM CourseMark cm JOIN cm.student s JOIN cm.course c WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();

        // Filter by studentId
        if (filter.getStudentId() != null) {
            queryBuilder.append("and cm.student.id = :studentId ");
            countBuilder.append("and cm.student.id = :studentId ");
            params.put("studentId", filter.getStudentId());
        }

        // Filter by courseId
        if (filter.getCourseId() != null) {
            queryBuilder.append("and cm.course.id = :courseId ");
            countBuilder.append("and cm.course.id = :courseId ");
            params.put("courseId", filter.getCourseId());
        }

        // Filter by mark range
        if (filter.getMarkFrom() != null) {
            queryBuilder.append("and cm.mark >= :markFrom ");
            countBuilder.append("and cm.mark >= :markFrom ");
            params.put("markFrom", filter.getMarkFrom());
        }
        if (filter.getMarkTo() != null) {
            queryBuilder.append("and cm.mark <= :markTo ");
            countBuilder.append("and cm.mark <= :markTo ");
            params.put("markTo", filter.getMarkTo());
        }

        // Filter by createdDateFrom
        if (filter.getCreatedDateFrom() != null) {
            queryBuilder.append("and cm.createdDate >= :createdDateFrom ");
            countBuilder.append("and cm.createdDate >= :createdDateFrom ");
            LocalDateTime fromDate = LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN);
            params.put("createdDateFrom", fromDate);
        }

        // Filter by createdDateTo
        if (filter.getCreatedDateTo() != null) {
            queryBuilder.append("and cm.createdDate <= :createdDateTo ");
            countBuilder.append("and cm.createdDate <= :createdDateTo ");
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX);
            params.put("createdDateTo", toDate);
        }

        // Filter by studentName
        if (filter.getStudentName() != null) {
            queryBuilder.append("AND LOWER(s.name) LIKE :studentName "); // Use the actual field name for student
            countBuilder.append("AND LOWER(s.name) LIKE :studentName ");
            params.put("studentName", "%" + filter.getStudentName().toLowerCase() + "%");
        }

        // Filter by courseName
        if (filter.getCourseName() != null) {
            queryBuilder.append("AND LOWER(c.name) LIKE :courseName "); // Use the actual field name for course
            countBuilder.append("AND LOWER(c.name) LIKE :courseName ");
            params.put("courseName", "%" + filter.getCourseName().toLowerCase() + "%");
        }

        // Create the select and count queries
        Query selectQuery = entityManager.createQuery(queryBuilder.toString(), CourseMark.class);
        Query countQuery = entityManager.createQuery(countBuilder.toString(), CourseMark.class );

        // Set the parameters
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        // Apply pagination
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        // Get results
        List<CourseMark> courseMarkList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new CustomFilterResponseDTO<>(courseMarkList, totalCount);
    }
}
