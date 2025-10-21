package annie470.U5_W3_D1.services;

import annie470.U5_W3_D1.entities.Dipendente;
import annie470.U5_W3_D1.exceptions.UnauthorizedException;
import annie470.U5_W3_D1.payloads.LoginDTO;
import annie470.U5_W3_D1.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

 public String checkDipendenteAndGenerateToken(LoginDTO body) {
     Dipendente found =dipendenteService.findByEmail(body.email());
     if(bcrypt.matches(body.password(), found.getPassword())){
         return  jwtTools.createToken(found);
     } else {
         throw  new UnauthorizedException("Credenziali errate!");
     }
 }
}
