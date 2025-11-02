package myproject.todolist.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// 1. IMPORT "HỢP ĐỒNG" CỦA SPRING SECURITY
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections; // Import Collections
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
// 2. "THỰC THI HỢP ĐỒNG"
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    // --- CÁC HÀM CỦA USERDETAILS BẮT ĐẦU TỪ ĐÂY ---
    // (Dán 7 hàm này vào)

    // 3. getAuthorities: Trả về "Quyền" (ví dụ: "ROLE_ADMIN", "ROLE_USER")
    //    Hiện tại, chúng ta chưa làm "Quyền", nên trả về danh sách rỗng.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // 4. getPassword: Spring Security sẽ gọi hàm này để lấy mật khẩu
    //    (Nó đã có sẵn vì Lombok @Data đã tạo 'getPassword()')
    //    public String getPassword() { return this.password; }

    // 5. getUsername: Spring Security sẽ dùng "email" làm "username"
    @Override
    public String getUsername() {
        return this.email;
    }

    // 6. 4 hàm dưới đây để kiểm tra tài khoản (hết hạn, bị khóa...)
    //    Hiện tại, chúng ta cho 'true' (luôn hợp lệ) hết.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}