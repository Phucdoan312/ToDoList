package myproject.todolist.config; // Hoặc 'myproject.todolist.security'

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// 1. BÁO SPRING: "Đây là một 'công cụ', hãy quản lý nó"
@Component
public class JwtTokenProvider {

    // 2. Lấy "Mực in" (Secret Key) từ file application.properties
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    // 3. Lấy "Hạn sử dụng" từ file application.properties
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationMs;

    // 4. HÀM CHÍNH 1: "IN VÉ" (Tạo Token)
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // "In vé": Ghi 'email' vào vé, đặt ngày hết hạn,
        // và "ký tên" bằng "mực bí mật" (Secret Key)
        return Jwts.builder()
                .setSubject(email) // "Chủ nhân" của vé
                .setIssuedAt(now) // Ngày phát hành
                .setExpiration(expiryDate) // Ngày hết hạn
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Ký tên
                .compact();
    }

    // 5. HÀM CHÍNH 2: "LẤY EMAIL TỪ VÉ" (Giải mã)
    public String getEmailFromToken(String token) {
        // Dùng "mực bí mật" để giải mã vé và lấy 'subject' (email)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // 6. HÀM CHÍNH 3: "KIỂM TRA VÉ" (Validate)
    public boolean validateToken(String token) {
        try {
            // Dùng "mực bí mật" để thử giải mã.
            // Nếu giải mã được (không văng lỗi) -> Vé hợp lệ
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // Nếu văng lỗi (ví dụ: hết hạn, chữ ký sai) -> Vé không hợp lệ
            return false;
        }
    }

    // 7. HÀM NỘI BỘ: Tạo "chìa khóa" từ "mực bí mật"
    private Key getSigningKey() {
        // Phải dùng Base64 decode cái Secret Key
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}