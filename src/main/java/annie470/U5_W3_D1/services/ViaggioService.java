package annie470.U5_W3_D1.services;

import annie470.U5_W3_D1.entities.Viaggio;
import annie470.U5_W3_D1.exceptions.NotFoundException;
import annie470.U5_W3_D1.payloads.ViaggioDTO;
import annie470.U5_W3_D1.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class ViaggioService {
    @Autowired
    private ViaggioRepository viaggioRepository;

    //GET ALL
    public Page<Viaggio> findAll(int pageN, int pageSize) {
        Pageable pageable = PageRequest.of(pageN, pageSize);
        return this.viaggioRepository.findAll(pageable);
    }

    //GET SINGLE
    public Viaggio findById(Long id) {
        return this.viaggioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaggio non presente in DB o id incorretto!"));
    }

    //POST
    public Viaggio saveViaggio(ViaggioDTO payload) {
        Viaggio newViaggio = new Viaggio(payload.destinazione(),payload.date(), payload.stato());
        return this.viaggioRepository.save(newViaggio);
    }

    //PUT
    public Viaggio getAndUpdate(Long id, ViaggioDTO payload) {
        Viaggio found = this.findById(id);
        found.setDestinazione(payload.destinazione());
        found.setData(payload.date());
        found.setStato(payload.stato());
        return viaggioRepository.save(found);
    }

    //DELETE
    public void findAndDelete(Long id) {
        Viaggio found = this.findById(id);
        this.viaggioRepository.delete(found);
    }

}