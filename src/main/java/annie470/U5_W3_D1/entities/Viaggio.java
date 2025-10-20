package annie470.U5_W3_D1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String destinazione;
    @Column(nullable = false)
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Stato stato;

    public Viaggio(String destinazione, LocalDate data, Stato stato) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
    }
}
