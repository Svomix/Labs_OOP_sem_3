package Labs_OOP_sem_3.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String SECRET_KEY = "233c484bda9f1689402a550dff29a20820ef8330882562c13caaf3f764cd84ddb163f714a36852cd1a89607fe9c5506180e01890324e0ea665f6016ffec16e65dfcf90e6636748c233fa78d2337367ce66c24717a012f84194ebe7dc065dacdc4dd37f0c8491d0cb06a63f8c4e33893a7a17073152d755bc33d0792d7fb4ea58f0519700c2a3bef4fbc27aa15ca06467a29089a385f7c4948ec1ce18f6c37b0e2e659b041ff02c71e9e4cc11fa7364f17480c2730dc4eb78cfe8f0acbaea4b6b89fb5c721c7def23a363368fc9dc804fe1bc369fd79fa818b6918bfc76dae729b2e2319c6660e873941ece4ba77cb471a512f8b090e2eacf41f18c59ddab52ab";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
