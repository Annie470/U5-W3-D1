package annie470.U5_W3_D1.repositories;

import annie470.U5_W3_D1.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    Optional<Prenotazione> findById(UUID id);

    boolean existsByDipendenteIdAndDataRichiesta(UUID dipendenteId, LocalDate dataRichiesta);

    @Query("SELECT p FROM Prenotazione p WHERE p.dipendente.id = :dipendenteId AND p.dataRichiesta = :dataRichiesta AND p.id != :id")
    Optional<Prenotazione> findPrenotazioneStessaData(@Param("dipendenteId") UUID dipendenteId, @Param("dataRichiesta") LocalDate dataRichiesta, @Param("id") UUID id);
}
