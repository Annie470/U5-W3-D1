package annie470.U5_W3_D1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "data_richiesta", nullable = false)
    private LocalDate dataRichiesta;
    @Column(nullable = true)
    private String note;

    @ManyToOne
    @JoinColumn(name = "viaggio_id", nullable = false)
    private Viaggio viaggio;
    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = false)
    private Dipendente dipendente;


    public Prenotazione( Viaggio viaggio, Dipendente dipendente, LocalDate dataRichiesta, String note) {
        this.viaggio = viaggio;
        this.dipendente = dipendente;
        this.dataRichiesta = dataRichiesta;
        this.note = note;
    }
}
