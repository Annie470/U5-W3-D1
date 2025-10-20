package annie470.U5_W3_D1.services;

import annie470.U5_W3_D1.entities.Dipendente;
import annie470.U5_W3_D1.entities.Prenotazione;
import annie470.U5_W3_D1.entities.Viaggio;
import annie470.U5_W3_D1.exceptions.NotFoundException;
import annie470.U5_W3_D1.exceptions.ValidationException;
import annie470.U5_W3_D1.payloads.PrenotazioneDTO;
import annie470.U5_W3_D1.payloads.UpdatePrenotazioneDTO;
import annie470.U5_W3_D1.repositories.DipendenteRepository;
import annie470.U5_W3_D1.repositories.PrenotazioneRepository;
import annie470.U5_W3_D1.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;

    //POST
    public Prenotazione savePrenotazione(PrenotazioneDTO payload) {
        List<String> errors = new ArrayList<>();
        Viaggio viaggioTrovato = viaggioRepository.findById(payload.viaggioId()).orElse(null);
        if (viaggioTrovato == null) {
            errors.add("Viaggio non trovato o id viaggio non corretto!");
        }Dipendente dipendenteTrovato = dipendenteRepository.findById(payload.dipendenteId())
                .orElse(null);
        if (dipendenteTrovato == null) {
            errors.add("Dipendente non trovato o id incorretto!");
        }
        //filtra se trovav dipendente id e ha stessa data?
        if (dipendenteTrovato != null && prenotazioneRepository.existsByDipendenteIdAndDataRichiesta(payload.dipendenteId(), payload.dataRichiesta())) {
            errors.add("Il dipendente è impegnato in altro viaggio per questa data!");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Prenotazione newPrenotazione = new Prenotazione(viaggioTrovato, dipendenteTrovato, payload.dataRichiesta(), payload.note());
        return this.prenotazioneRepository.save(newPrenotazione);
    }

    //GET ALL
    public Page<Prenotazione> findAll(int pageN, int pageSize) {
        Pageable pageable = PageRequest.of(pageN, pageSize);
        return this.prenotazioneRepository.findAll(pageable);
    }

    //GET SINGLE
    public Prenotazione findById(UUID id) {
        return this.prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non presente in DB o id incorretto!"));
    }

    //PUT
    public Prenotazione getAndUpdate(UUID id, UpdatePrenotazioneDTO payload) {
        Prenotazione found = this.findById(id);
        Viaggio viaggio = viaggioRepository.findById(payload.viaggioId()).orElseThrow(() -> new NotFoundException("Viaggio non presente in DB o id incorretto!"));

        //non si puo camvìbiare data se il dipendente assegnato a questo viaggio ha gia la stessa data
        prenotazioneRepository.findPrenotazioneStessaData(found.getDipendente().getId(), payload.dataRichiesta(), id).ifPresent(p -> {
            throw new ValidationException(List.of("Il dipendente è già occupato in un viaggio per questa data!"));
        });
        found.setViaggio(viaggio);
        found.setDataRichiesta(payload.dataRichiesta());
        found.setNote(payload.note());
        return prenotazioneRepository.save(found);
    }

    //DELETE
    public void findAndDelete(UUID id) {
        Prenotazione found = this.findById(id);
        this.prenotazioneRepository.delete(found);
    }

}
