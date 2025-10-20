package annie470.U5_W3_D1.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsListDTO (
        String message,
        LocalDateTime timestamp,
        List<String> errors) {
}