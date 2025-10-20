package annie470.U5_W3_D1.controllers;

import annie470.U5_W3_D1.entities.Dipendente;
import annie470.U5_W3_D1.exceptions.ValidationException;
import annie470.U5_W3_D1.payloads.LoginDTO;
import annie470.U5_W3_D1.payloads.LoginResponseDTO;
import annie470.U5_W3_D1.payloads.NewDipendenteDTO;
import annie470.U5_W3_D1.services.AuthService;
import annie470.U5_W3_D1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DipendenteService dipendenteService;


    //LOGIN
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return new LoginResponseDTO(authService.checkDipendenteAndGenerateToken(body));
    }

    //POST
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente salvaDipendente(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.dipendenteService.saveDipendente(body);
    }
}
