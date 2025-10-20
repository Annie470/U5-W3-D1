package annie470.U5_W3_D1.payloads;

import annie470.U5_W3_D1.entities.Stato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank
        String destinazione,
        @NotNull
        LocalDate date,
        @NotNull
        Stato stato) {}
