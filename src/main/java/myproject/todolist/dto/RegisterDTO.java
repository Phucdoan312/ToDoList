package myproject.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
// Bạn có thể thêm validation sau
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RegisterDTO {

    // @NotEmpty
    // @Email
    private String email;

    // @NotEmpty
    // @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    // (Bạn có thể thêm 1 trường 'confirmPassword' ở đây để
    //  logic Service kiểm tra, nhưng CSDL không lưu)
}