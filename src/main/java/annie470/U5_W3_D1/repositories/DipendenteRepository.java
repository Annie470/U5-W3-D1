package annie470.U5_W3_D1.repositories;


import annie470.U5_W3_D1.entities.Dipendente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {

    Optional<Dipendente> findById(UUID id);
    Optional<Dipendente> findByEmail(String email);
    Optional<Dipendente> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
