package myproject.todolist.model; // 1. "Địa chỉ" của file

// 2. Import "công cụ" từ các thư viện
import jakarta.persistence.*; // Dùng cho CSDL (@Entity, @Id...)
import lombok.Data; // Dùng để code "sạch"
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // 3. Của Lombok: Tự động tạo Getter, Setter, toString()...
@NoArgsConstructor // 4. Của Lombok: Tự tạo Constructor rỗng (JPA cần)
@AllArgsConstructor // 5. Của Lombok: Tự tạo Constructor có đủ tham số

@Entity // 6. BÁO CHO JPA: "Đây là 1 thực thể, hãy quản lý nó"
@Table(name = "tasks") // 7. Tên chính xác của bảng trong CSDL
public class Task {

    @Id // 8. ĐÁNH DẤU: Đây là Khóa chính (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 9. TỰ ĐỘNG TĂNG
    private Long id;

    @Column(nullable = false) // 10. CỘT: Không được phép rỗng
    private String title;

    private boolean isCompleted; // 11. CỘT: Mặc định là 'false'

    @ManyToOne // "Nhiều" Task cho "Một" User
    @JoinColumn(name = "user_id") // Tên của cột khóa ngoại
    private User user;
}