package code.doston.dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarkResponseWithDetailDTO {

    private String studentName;
    private String courseName;
    private LocalDateTime createdDate;
    private Integer mark;
}
