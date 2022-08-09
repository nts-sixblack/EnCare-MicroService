package nts.sixblack.userservice.jwt;

import io.jsonwebtoken.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${application.security.secret-key}")
    private String jwtSecretKey;

    public long getUserId(String token){
        if (validateToken(token)) {
            return Long.parseLong(Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject());
        }
        return 0;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
