package in.madhu.ecommerce.app.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import in.madhu.ecommerce.app.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	
	private final JwtProperties jwtProperties;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret()
                        .getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Customer customer) {

        Date now = new Date();

        Date expiry = new Date(
                now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()

                .setSubject(customer.getEmail())

                .claim("role", customer.getRole().name())

                .setIssuedAt(now)

                .setExpiration(expiry)

                .signWith(getSigningKey())

                .compact();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    public boolean isTokenValid(String token,
                                String email) {

        return extractUsername(token).equals(email)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)

                .getExpiration()

                .before(new Date());
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey((javax.crypto.SecretKey) getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

}
