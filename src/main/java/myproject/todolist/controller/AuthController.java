package myproject.todolist.controller;

import myproject.todolist.dto.AuthResponseDTO;
import myproject.todolist.dto.LoginDTO;
import myproject.todolist.dto.RegisterDTO;
import myproject.todolist.service.AuthService; // 1. Import "Hợp đồng Logic"
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 2. Báo Spring: Đây là API Controller, trả về JSON
@RequestMapping("/api/auth") // 3. "Địa chỉ" gốc
@CrossOrigin(origins = "http://localhost:5173") // 4. Cho phép React gọi
public class AuthController {

    @Autowired
    private AuthService authService; // 5. "Tiêm" Hợp đồng (Menu)

    // 6. API 1: ĐĂNG NHẬP (LOGIN)
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        // Gọi "Bộ não" để xử lý
        AuthResponseDTO response = authService.login(loginDTO);

        // Trả về "Cái vé" (Token) và status 200 OK
        return ResponseEntity.ok(response);
    }

    // 7. API 2: ĐĂNG KÝ (REGISTER)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        // Gọi "Bộ não" để xử lý
        String responseMessage = authService.register(registerDTO);

        // Trả về thông báo (String) và status 200 OK
        return ResponseEntity.ok(responseMessage);
    }
}