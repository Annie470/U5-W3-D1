package annie470.U5_W3_D1.services;

import annie470.U5_W3_D1.entities.Dipendente;
import annie470.U5_W3_D1.exceptions.UnauthorizedException;
import annie470.U5_W3_D1.payloads.LoginDTO;
import annie470.U5_W3_D1.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JwtTools jwtTools;

 public String checkDipendenteAndGenerateToken(LoginDTO body) {
     Dipendente found =dipendenteService.findByEmail(body.email());
     if(found.getPassword().equals((body.password()))){
         return  jwtTools.createToken(found);
     } else {
         throw  new UnauthorizedException("Credenziali errate!");
     }
 }
}
