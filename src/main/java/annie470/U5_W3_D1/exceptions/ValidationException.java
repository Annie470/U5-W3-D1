package annie470.U5_W3_D1.exceptions;

import lombok.Getter;

import java.util.List;
@Getter
public class ValidationException extends RuntimeException {
    private List<String> errors;
    public ValidationException(List<String> errors) {

        super("Errori nella validazoione del payload");
        this.errors = errors;
    }
}
