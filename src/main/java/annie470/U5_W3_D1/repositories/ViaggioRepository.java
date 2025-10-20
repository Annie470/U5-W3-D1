package annie470.U5_W3_D1.repositories;

import annie470.U5_W3_D1.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {

    Optional<Viaggio> findById(Long aLong);

}
