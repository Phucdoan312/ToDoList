package myproject.todolist.service;

import myproject.todolist.dto.AuthResponseDTO;
import myproject.todolist.dto.LoginDTO;
import myproject.todolist.dto.RegisterDTO;
import myproject.todolist.config.JwtTokenProvider; // 1. Import "Máy In Vé"
import myproject.todolist.model.User;
import myproject.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager; // 2. Import "Máy Quản lý"
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder; // 3. Import "Máy Băm"
import org.springframework.stereotype.Service;

@Service // 4. BÁO SPRING: "Đây là Bean Dịch vụ"
public class AuthServiceImpl implements AuthService { // 5. "Thực thi Hợp đồng"

    // 6. "Tiêm" (Inject) tất cả các "công cụ" cần thiết
    @Autowired
    private AuthenticationManager authenticationManager; // "Máy Quản lý" (từ SecurityConfig)

    @Autowired
    private UserRepository userRepository; // "Thủ kho" User

    @Autowired
    private PasswordEncoder passwordEncoder; // "Máy Băm" (từ SecurityConfig)

    @Autowired
    private JwtTokenProvider tokenProvider; // "Máy In Vé"

    // 7. Logic Nghiệp vụ 1: ĐĂNG NHẬP (LOGIN)
    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {

        // B1: "Thử" đăng nhập
        //      Đưa 'email' (username) và 'password' (chưa băm) cho "Máy Quản lý"
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        // B2: Nếu (B1) không văng lỗi (tức là đăng nhập thành công)
        //     "Đóng dấu" (authenticate) cho phiên này
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // B3: Dùng "Máy In Vé" để tạo 1 "vé" mới
        String token = tokenProvider.generateToken(loginDTO.getEmail());

        // B4: Gửi "vé" về cho React
        return new AuthResponseDTO(token);
    }

    // 8. Logic Nghiệp vụ 2: ĐĂNG KÝ (REGISTER)
    @Override
    public String register(RegisterDTO registerDTO) {

        // B1: Kiểm tra xem User (email) đã tồn tại chưa?
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            // Nếu đã tồn tại, văng lỗi (hoặc trả về thông báo lỗi)
            throw new RuntimeException("Email đã được sử dụng!");
        }

        // B2: Nếu email mới, tạo 1 User mới
        User user = new User();
        user.setEmail(registerDTO.getEmail());

        // B3: Dùng "Máy Băm" để băm mật khẩu
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // B4: Dùng "Thủ kho" để lưu User mới vào CSDL
        userRepository.save(user);

        return "Đăng ký thành công!";
    }
}