package annie470.U5_W3_D1.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
public record NewDipendenteDTO(
        @NotBlank
        String nome,
        @NotBlank
        String cognome,
        @NotBlank
        String username,
        @Email
        @NotBlank
        String email
) {}
