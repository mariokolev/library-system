package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.dto.LoginResponseDTO;
import bg.tu.varna.informationSystem.entity.User;
import bg.tu.varna.informationSystem.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final String BEARER_ID_FIELD = "id";
    private static final String BEARER_SUBJECT_FIELD = "sub";
    private static final String BEARER_PERMISSIONS = "permissions";
    private static final String BEARER_ROLE = "role";

    public LoginResponseDTO generateToken(User user, List<String> permissions, String roleName) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        String compactTokenString = Jwts.builder()
                .claim(BEARER_ID_FIELD, user.getId())
                .claim(BEARER_SUBJECT_FIELD, user.getUsername())
                .claim(BEARER_PERMISSIONS, permissions)
                .claim(BEARER_ROLE, roleName)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new LoginResponseDTO(compactTokenString, permissions, roleName);
    }

    /**
     * @param token - the compact token
     */
    public UserPrincipal parseToken(String token) {
        byte[] secretBytes = jwtSecret.getBytes();

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);

        String username = jwsClaims.getBody()
                .getSubject();
        Long userId = jwsClaims.getBody()
                .get(BEARER_ID_FIELD, Long.class);
        List<String> permissions = jwsClaims.getBody().get(BEARER_PERMISSIONS, List.class);

        String roleName = jwsClaims.getBody().get(BEARER_ROLE, String.class);

        return new UserPrincipal(userId, username, permissions, roleName);
    }
}
