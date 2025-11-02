package myproject.todolist.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myproject.todolist.config.JwtTokenProvider; // 1. Import "Máy In Vé"
import myproject.todolist.service.CustomUserDetailsService; // 2. Import "Người Tìm User"
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 3. BÁO SPRING: "Đây là một 'công cụ', hãy quản lý nó"
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 4. "Lính gác" 1 lần/request

    @Autowired
    private JwtTokenProvider tokenProvider; // 5. "Tiêm" Máy In Vé

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // 6. "Tiêm" Người Tìm User

    // 7. HÀM CHÍNH: "Kiểm tra vé"
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // B1: Lấy "vé" (Token) từ request
        String token = getJwtFromRequest(request);

        // B2: "Kiểm tra vé"
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {

            // B3: Nếu vé hợp lệ, "Lấy email từ vé"
            String email = tokenProvider.getEmailFromToken(token);

            // B4: Dùng email, "Tìm chủ nhân" vé trong CSDL
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // B5: "Đóng dấu" (Authenticate)
            //     Tạo một "chứng nhận" (Token) và "đặt" vào SecurityContextHolder
            //     Spring Security sẽ hiểu là request này "OK, đã xác thực"
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // B6: Cho request "qua cửa"
        filterChain.doFilter(request, response);
    }

    // HÀM PHỤ: Lấy "vé" từ Header
    private String getJwtFromRequest(HttpServletRequest request) {
        // "Vé" thường được gửi trong Header: "Authorization: Bearer <token>"
        String bearerToken = request.getHeader("Authorization");

        // Kiểm tra xem có "vé" không và có đúng chuẩn "Bearer" không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Cắt lấy phần "vé" (bỏ "Bearer ")
            return bearerToken.substring(7);
        }
        return null;
    }
}