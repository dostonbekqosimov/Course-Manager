package code.doston.repository.filterRepository;

import code.doston.dtos.CustomFilterResponseDTO;
import code.doston.dtos.filterDTOs.StudentFilterDTO;
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
public class CustomStudentFilterRepository {

    private final EntityManager entityManager;

    public CustomFilterResponseDTO<Student> filter(StudentFilterDTO filter, int page, int size) {
        StringBuilder conditionBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filter.getName() != null) {
            conditionBuilder.append("and lower(s.name) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        if (filter.getSurname() != null) {
            conditionBuilder.append("and lower(s.surname) like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }

        if (filter.getAge() != null) {
            conditionBuilder.append("and s.age =:age ");
            params.put("age", filter.getAge());
        }
        if (filter.getLevel() != null) {
            conditionBuilder.append("and s.level =:level ");
            params.put("level", filter.getLevel());
        }
        if (filter.getGender() != null) {
            conditionBuilder.append("and s.gender =:gender ");
            params.put("gender", filter.getGender());
        }
        if (filter.getCreatedDateFrom() != null) {
            conditionBuilder.append("and s.createdDate >=:from ");
            LocalDateTime fromDate = LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN);
            params.put("from", fromDate);
        }

        if (filter.getCreatedDateTo() != null) {
            conditionBuilder.append("and s.createdDate <=:to ");
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX);
            params.put("to", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder("From Student as s where 1=1 ");
        selectBuilder.append(conditionBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) From Student as s where 1=1 ");
        countBuilder.append(conditionBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString(), Student.class);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        // Apply pagination
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        List<Student> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new CustomFilterResponseDTO<>(entityList, totalCount);
    }
}