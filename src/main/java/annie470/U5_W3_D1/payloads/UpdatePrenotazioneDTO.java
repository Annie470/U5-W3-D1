package annie470.U5_W3_D1.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record UpdatePrenotazioneDTO(
        @NotNull
        long viaggioId,
        @NotNull
        LocalDate dataRichiesta,
        @Size(max = 50)
        String note
) {
}
