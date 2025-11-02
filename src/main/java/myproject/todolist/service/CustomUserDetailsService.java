package myproject.todolist.service;

import myproject.todolist.model.User;
import myproject.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 1. BÁO SPRING: "Đây là Service 'Tìm User' đặc biệt"
@Service
public class CustomUserDetailsService implements UserDetailsService { // 2. "Thực thi Hợp đồng"

    @Autowired
    private UserRepository userRepository; // 3. "Tiêm" Thủ kho User

    // 4. HÀM QUAN TRỌNG NHẤT
    //    Spring Security sẽ tự động gọi hàm này khi "kiểm tra vé"
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 5. Dùng "Thủ kho" để tìm User trong CSDL bằng email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy User với email: " + email));

        // 6. Trả về User (vì User đã 'implements UserDetails')
        return user;
    }
}