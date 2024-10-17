package code.doston.repository.filterRepository;

import code.doston.dtos.filterDTOs.CourseFilterDTO;
import code.doston.dtos.CustomFilterResponseDTO;
import code.doston.entity.Course;
import code.doston.entity.Student;
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
public class CustomCourseFilterRepository {

    private final EntityManager entityManager;

    public CustomFilterResponseDTO<Course> filter(CourseFilterDTO filter, int page, int size) {

        StringBuilder conditionBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filter.getName() != null) {
            conditionBuilder.append("and lower(c.name) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        if (filter.getDescription() != null) {
            conditionBuilder.append("and lower(c.description) like :description ");
            params.put("description", "%" + filter.getDescription().toLowerCase() + "%");
        }
        if (filter.getPrice() != null) {
            conditionBuilder.append("and c.price =:price ");
            params.put("price", filter.getPrice());
        }
        if (filter.getDuration() != null) {
            conditionBuilder.append("and c.duration =:duration ");
            params.put("duration", filter.getDuration());
        }

        if (filter.getCreatedDateFrom() != null) {
            conditionBuilder.append("and c.createdDate >=:from ");
            LocalDateTime fromDate = LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN);
            params.put("from", fromDate);
        }

        if (filter.getCreatedDateTo() != null) {
            conditionBuilder.append("and c.createdDate <=:to ");
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX);
            params.put("to", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder("From Course as c where 1=1 ");
        selectBuilder.append(conditionBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) From Course as c where 1=1 ");
        countBuilder.append(conditionBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString(), Course.class);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        List<Course> courseList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new CustomFilterResponseDTO<Course>(courseList, totalCount);

    }

}
