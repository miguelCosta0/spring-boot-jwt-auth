package jwt_auth.auth;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jwt_auth.User.User;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${jwt.key}") String b64key) {
        key = new SecretKeySpec(Base64.getDecoder().decode(b64key), "HmacSHA256");
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        long expSeconds = 60 * 60 * 24 * 30; // 1 month

        return Jwts.builder()
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(expSeconds)))
            .subject(user.getEmail())
            .signWith(key)
            .compact();
    }

    public String getSubjectFromToken(String jwt) {
        try {
            Jws<Claims> jws = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt);
            return jws.getPayload().getSubject();
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }
}
