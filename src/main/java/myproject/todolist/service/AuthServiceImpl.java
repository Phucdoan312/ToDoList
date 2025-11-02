package myproject.todolist.service;

import myproject.todolist.dto.AuthResponseDTO;
import myproject.todolist.dto.LoginDTO;
import myproject.todolist.dto.RegisterDTO;
import myproject.todolist.config.JwtTokenProvider;
import myproject.todolist.model.User;
import myproject.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ⬅️ IMPORT QUAN TRỌNG

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Sử dụng Constructor Injection (private final) - CHUẨN
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    // --- LOGIC AUTHENTICATION (Đăng nhập) ---
    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {

        // B1: "LÀM SẠCH" email đầu vào trước khi xác thực (Tránh lỗi)
        String cleanEmail = loginDTO.getEmail().trim().toLowerCase();

        // B2: "Thử" đăng nhập (Ủy thác cho AuthenticationManager)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        cleanEmail,
                        loginDTO.getPassword()
                )
        );

        // B3: "Đóng dấu" (Authenticate) cho phiên
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // B4: Lấy email ĐÃ ĐƯỢC XÁC THỰC (sạch) từ 'authentication'
        String authenticatedEmail = authentication.getName();

        // B5: Dùng email "sạch" để tạo "vé"
        String token = tokenProvider.generateToken(authenticatedEmail);

        return new AuthResponseDTO(token);
    }

    // --- LOGIC AUTHORIZATION (Đăng ký) ---
    @Transactional // ⬅️ THÊM CÁI NÀY ĐỂ BẢO ĐẢM GIAO DỊCH ĐƯỢC CAM KẾT (FIX BUG)
    @Override
    public String register(RegisterDTO registerDTO) {

        // B1: "LÀM SẠCH" (Normalize) email trước khi kiểm tra
        String cleanEmail = registerDTO.getEmail().trim().toLowerCase();

        // B2: Dùng email "sạch" để kiểm tra tồn tại
        if (userRepository.findByEmail(cleanEmail).isPresent()) {
            // NÊN tạo một Exception tùy chỉnh để xử lý lỗi 409 Conflict (tồn tại)
            throw new RuntimeException("Email đã được sử dụng!");
        }

        // B3: Nếu email mới, tạo 1 User mới
        User user = new User();
        user.setEmail(cleanEmail); // LƯU EMAIL "SẠCH"

        // B4: Dùng "Máy Băm" để băm mật khẩu
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        System.out.println(">>> [AuthService] Đang chuẩn bị lưu User: " + cleanEmail);
        // B5: Dùng "Thủ kho" để lưu User mới vào CSDL (Commit sẽ xảy ra sau khi hàm này kết thúc)
        userRepository.save(user);

        return "Đăng ký thành công!";
    }

    // --- LOGIC TIỆN ÍCH (Cho TaskService) ---
    @Override
    public User getCurrentUser() {

        // B1: Lấy email của người dùng từ SecurityContextHolder
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // B2: Dùng email để tìm User Entity trong CSDL (Đây là email đã được làm sạch)
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in DB. Email: " + userEmail));
    }
}