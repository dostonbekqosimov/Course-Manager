package code.doston.exceptions;

import lombok.Data;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import lombok.Getter;

@Getter
public class IdExistsException extends RuntimeException {
    private Long id;

    public IdExistsException(String message) {
        super(message);
    }

    public IdExistsException(String message, Long id) {
        super(message + id);
        this.id = id;
    }
}
