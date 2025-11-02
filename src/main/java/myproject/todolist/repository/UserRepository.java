package myproject.todolist.repository;

import myproject.todolist.model.User; // 1. Import "Bản vẽ" User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // 2. Import "Optional"

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // 3. "Phép thuật" CRUD

    // 4. ĐÂY LÀ HÀM QUAN TRỌNG NHẤT (KHÔNG ĐỂ TRỐNG)

    /**
     * Tìm một User bằng địa chỉ email.
     * Đây là "Query Ma thuật" (Derived Query).
     * @param email Email cần tìm.
     * @return Một Optional<User> (có thể chứa User hoặc rỗng)
     */
    Optional<User> findByEmail(String email); // 5.
}