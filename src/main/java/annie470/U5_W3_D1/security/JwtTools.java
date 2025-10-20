package annie470.U5_W3_D1.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTools {
    @Value("${jwt.secret}")
    private String secret;
}
