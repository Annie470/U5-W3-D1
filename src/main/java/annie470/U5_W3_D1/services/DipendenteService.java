package annie470.U5_W3_D1.services;

import annie470.U5_W3_D1.entities.Dipendente;
import annie470.U5_W3_D1.exceptions.BadRequestException;
import annie470.U5_W3_D1.exceptions.NotFoundException;
import annie470.U5_W3_D1.exceptions.ValidationException;
import annie470.U5_W3_D1.payloads.NewDipendenteDTO;
import annie470.U5_W3_D1.repositories.DipendenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private Cloudinary imageUploader;
    @Autowired
    private PasswordEncoder bcrypt;

    private static final long MAX_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOWED_TYPES = Set.of( //GRAZIE INTERNET
            "image/jpg",
            "image/png",
            "image/jpeg"
    );

    //GET ALL
    public Page<Dipendente> findAll(int pageN, int pageSize) {
        Pageable pageable = PageRequest.of(pageN, pageSize);
        return this.dipendenteRepository.findAll(pageable);}

    //GET SINGLE
    public  Dipendente findById(UUID id) { return this.dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Dipendente non presente in DB o id incorretto!"));}
    public  Dipendente findByEmail(String email) { return this.dipendenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente inesistente o email incorretta"));}
    
    
    //POST
    public Dipendente saveDipendente(NewDipendenteDTO payload){
        List<String> errors = new ArrayList<>();
        if (dipendenteRepository.existsByEmail(payload.email())) {
            errors.add("Email già in uso!");
        }
        if (dipendenteRepository.existsByUsername(payload.username())) {
            errors.add("Username già in uso!");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Dipendente newDipendente = new Dipendente(payload.nome(), payload.cognome(), payload.username(), payload.email(), bcrypt.encode(payload.password()));
        newDipendente.setAvatar("https://ui-avatars.com/api/?name=" + payload.nome() + "+" + payload.cognome());
        this.dipendenteRepository.save(newDipendente);
        return newDipendente;
    }

    //PUT
    public Dipendente getAndUpdate(UUID id, NewDipendenteDTO payload) {
        Dipendente found = this.findById(id);
        this.dipendenteRepository.findByEmail(payload.email()).ifPresent(dipendente -> {if(dipendente.getId() != id) {
            throw new BadRequestException("Email già in utilizzo da altro dipendente!");
        }});
        this.dipendenteRepository.findByUsername(payload.username()).ifPresent(dipendente -> {
            if(!dipendente.getId().equals(id)) {
                throw new BadRequestException("Username già in utilizzo da altro dipendente!");
            }
        });
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setUsername(payload.username());
        found.setEmail(payload.email());
        return dipendenteRepository.save(found);
    }

    //DELETE
    public void findAndDelete(UUID id) {
        Dipendente found = this.findById(id);
        this.dipendenteRepository.delete(found);
    }


    //PATCH AVATAR
    public Dipendente uploadAvatar(MultipartFile file, UUID id) {
        Dipendente found = this.findById(id);
        if (file.isEmpty()) throw new BadRequestException("File vuoto!");
        if (file.getSize() > MAX_SIZE) throw new BadRequestException("File troppo grande!");
        if (!ALLOWED_TYPES.contains(file.getContentType())) throw new BadRequestException("I formati permessi sono png e jpeg!");

        try {
            Map result = imageUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl= (String) result.get("url");
            found.setAvatar(imageUrl);
        } catch (IOException ex) {
            throw  new RuntimeException(ex);
        }
        this.dipendenteRepository.save(found);
        return found;
    }


}
