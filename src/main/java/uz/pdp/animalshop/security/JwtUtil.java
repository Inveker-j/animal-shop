package uz.pdp.animalshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.pdp.animalshop.dto.SaveUserDTO;

import javax.crypto.SecretKey;
import java.awt.font.ShapeGraphicAttribute;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    public String generateToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer("pdp.uz")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 20))
                .signWith(getKey())
                .claim("roles", roles)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer("pdp.uz")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getKey())
                .claim("roles", roles)
                .compact();
    }

    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode("123h56789012t456789012345678901r345678901234567h9012345678906l90");
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public List<GrantedAuthority> getRoles(String token) {
        String roles = getClaims(token).get("roles", String.class);
        return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String generateRandomAccessToken(SaveUserDTO userDto, String randomCode) {
        return Jwts.builder()
                .subject(userDto.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getKey())
                .claim("email", userDto.getEmail())
                .claim("firstName", userDto.getFirstName())
                .claim("lastName", userDto.getLastName())
                .claim("password", userDto.getPassword())
                .claim("randomNumber", randomCode)
                .compact();
    }
}
