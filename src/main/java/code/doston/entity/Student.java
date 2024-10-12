package code.doston.entity;

import code.doston.entity.enums.Gender;
import code.doston.entity.enums.Level;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // buni coursedagiga o'xshab qilsa ham bo'ladi.
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

}
