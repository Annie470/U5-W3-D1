package annie470.U5_W3_D1.controllers;


import annie470.U5_W3_D1.entities.Dipendente;
import annie470.U5_W3_D1.exceptions.ValidationException;
import annie470.U5_W3_D1.payloads.NewDipendenteDTO;
import annie470.U5_W3_D1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        @PreAuthorize("hasAuthority('ADMIN', 'USER')")
        public Page<Dipendente> getAll(@RequestParam(defaultValue = "0") int pageN, @RequestParam(defaultValue = "10") int pageSize) {
            return this.dipendenteService.findAll(pageN, pageSize);
    }

    //GET SINGLE
        @GetMapping("/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public Dipendente getById(@PathVariable UUID id) {
            return this.dipendenteService.findById(id);
    }
        @GetMapping("/me")
        public Dipendente getProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    //PUT
        @PutMapping("/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public Dipendente modifyDipendente(@PathVariable UUID id, @RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
            if(validationResult.hasErrors()) { throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());}
            return this.dipendenteService.getAndUpdate(id, body);
    }
        @PutMapping("/me")
        public Dipendente updateProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedUser, @RequestBody NewDipendenteDTO body) {
        return this.dipendenteService.getAndUpdate(currentAuthenticatedUser.getId(), body);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void delete(@PathVariable UUID id) {
        this.dipendenteService.findAndDelete(id);
    }
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Dipendente currentAuthenticatedUser) {
        this.dipendenteService.findAndDelete(currentAuthenticatedUser.getId());
    }

    //PATCH ADD AVATAR
    @PatchMapping("/{id}/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente updateImg(@PathVariable UUID id, @RequestParam("avatar") MultipartFile file) throws IOException {
        return this.dipendenteService.uploadAvatar(file, id);
    }
    @PatchMapping("/me/upload")
    public Dipendente updateImg(@AuthenticationPrincipal Dipendente currentAuthenticatedUser, @RequestParam("avatar") MultipartFile file) throws IOException {
        return this.dipendenteService.uploadAvatar(file, currentAuthenticatedUser.getId());
    }

}
