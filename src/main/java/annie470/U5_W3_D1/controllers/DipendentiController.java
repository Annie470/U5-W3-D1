package annie470.U5_W3_D1.controllers;


import annie470.U5_W3_D1.entities.Dipendente;
import annie470.U5_W3_D1.exceptions.ValidationException;
import annie470.U5_W3_D1.payloads.NewDipendenteDTO;
import annie470.U5_W3_D1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    private DipendenteService dipendenteService;


    //GET ALL
        @GetMapping
        public Page<Dipendente> getAll(@RequestParam(defaultValue = "0") int pageN, @RequestParam(defaultValue = "10") int pageSize) {
            return this.dipendenteService.findAll(pageN, pageSize);
    }

    //GET SINGLE
        @GetMapping("/{id}")
        public Dipendente getById(@PathVariable UUID id) {
            return this.dipendenteService.findById(id);
    }

    //PUT
        @PutMapping("/{id}")
        public Dipendente modifyDipendente(@PathVariable UUID id, @RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
            if(validationResult.hasErrors()) { throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());}
            return this.dipendenteService.getAndUpdate(id, body);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void delete(@PathVariable UUID id) {
        this.dipendenteService.findAndDelete(id);
    }

    //PATCH ADD AVATAR
    @PatchMapping("/{id}/upload")
    public Dipendente updateImg(@PathVariable UUID id, @RequestParam("avatar") MultipartFile file) throws IOException {
        return this.dipendenteService.uploadAvatar(file, id);
    }
}
