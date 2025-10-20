package annie470.U5_W3_D1.controllers;

import annie470.U5_W3_D1.entities.Viaggio;
import annie470.U5_W3_D1.exceptions.ValidationException;
import annie470.U5_W3_D1.payloads.ViaggioDTO;
import annie470.U5_W3_D1.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    @Autowired
    private ViaggioService viaggioService;

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio salvaViaggio(@RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.viaggioService.saveViaggio(body);
    }

    //GET ALL
    @GetMapping
    public Page<Viaggio> getAll(@RequestParam(defaultValue = "0") int pageN, @RequestParam(defaultValue = "10") int pageSize) {
        return this.viaggioService.findAll(pageN, pageSize);
    }

    //GET SINGLE
    @GetMapping("/{id}")
    public Viaggio getById(@PathVariable Long id) {
        return this.viaggioService.findById(id);
    }

    //PUT
    @PutMapping("/{id}")
    public Viaggio modifyViaggio(@PathVariable Long id, @RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());}
        return this.viaggioService.getAndUpdate(id, body);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.viaggioService.findAndDelete(id);
    }
}
