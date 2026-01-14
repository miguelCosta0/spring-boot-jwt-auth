package jwt_auth.auth;

import java.time.Instant;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import jwt_auth.User.User;

@Service
public class JwtService {

    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        long expirySeconds = 60 * 60 * 24 * 30; // 1 month

        var jwtClaims = JwtClaimsSet
            .builder()
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expirySeconds))
            .subject(user.getEmail())
            .build();

        return encoder
            .encode(JwtEncoderParameters.from(jwtClaims))
            .getTokenValue();
    }

}
