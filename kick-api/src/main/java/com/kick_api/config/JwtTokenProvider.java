package com.kick_api.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // Thời gian sống của Access Token (mặc định 24h = 86400000ms)
    @Value("${app.jwt.expiration-in-ms}")
    private long jwtExpirationInMs;

    // Thời gian sống của Refresh Token (mặc định 7 ngày = 604800000ms)
    @Value("${app.jwt.refresh-expiration-in-ms}")
    private long refreshExpirationInMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Sinh Access Token JWT ngắn hạn chứa email và danh sách roles của user
     */
    public String generateAccessToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Sinh một chuỗi Refresh Token ngẫu nhiên (UUID) - không cần mã hóa JWT
     * vì nó sẽ được lưu thẳng vào cột refresh_token trong bảng users và đối chiếu trực tiếp
     */
    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Tính thời điểm hết hạn của Refresh Token dựa trên cấu hình
     */
    public Instant calculateRefreshTokenExpiry() {
        return Instant.now().plusMillis(refreshExpirationInMs);
    }

    /**
     * Trích xuất email của người dùng từ Access Token JWT
     */
    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Xác thực tính hợp lệ và thời hạn của Access Token JWT
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Token JWT không hợp lệ!");
        } catch (ExpiredJwtException ex) {
            log.error("Token JWT đã hết hạn!");
        } catch (UnsupportedJwtException ex) {
            log.error("Token JWT không được hỗ trợ!");
        } catch (IllegalArgumentException ex) {
            log.error("Chuỗi claims JWT trống!");
        } catch (SecurityException ex) {
            log.error("Chữ ký JWT không hợp lệ!");
        }
        return false;
    }
}
