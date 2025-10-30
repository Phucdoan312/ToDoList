package myproject.todolist.service; // 1. "Địa chỉ"

// 2. Import "Hợp đồng API" (DTOs)
import myproject.todolist.dto.AuthResponseDTO;
import myproject.todolist.dto.LoginDTO;
import myproject.todolist.dto.RegisterDTO;

// 3. Đây là "Hợp đồng Logic" cho Xác thực
public interface AuthService {

    /**
     * Nghiệp vụ 1: Đăng ký một User mới.
     * Sẽ kiểm tra email, "băm" mật khẩu, và lưu User.
     * @param registerDTO Thông tin đăng ký từ API
     * @return Một thông báo (String) thành công hoặc lỗi
     */
    String register(RegisterDTO registerDTO); // 4.

    /**
     * Nghiệp vụ 2: Đăng nhập một User.
     * Sẽ kiểm tra email, so sánh mật khẩu, và tạo "vé".
     * @param loginDTO Thông tin đăng nhập từ API
     * @return Một DTO chứa "Cái vé" (Access Token)
     */
    AuthResponseDTO login(LoginDTO loginDTO); // 5.
}