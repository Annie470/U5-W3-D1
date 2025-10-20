package annie470.U5_W3_D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @Email
        String email,
        @Size(min= 5,  message = "La password deve avere minimo 4 caratteri")
        @Pattern(regexp = "^(?=.*[A-Z])(?=(?:.*\\d.*){2}).{8,}$", message = "La password deve contenere almeno 8 caratteri, una lettera maiuscola e due numeri")
        String password) {
}
