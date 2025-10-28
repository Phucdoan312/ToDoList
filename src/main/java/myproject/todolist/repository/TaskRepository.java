package myproject.todolist.repository; // 1. "Địa chỉ" của file

// 2. Import "công cụ"
import myproject.todolist.model.Task; // Import cái "Bản vẽ" (Model)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 3. BÁO CHO SPRING: "Đây là Bean quản lý CSDL"
public interface TaskRepository extends JpaRepository<Task, Long> { // 4. "PHÉP THUẬT"

    // 5. BẠN ĐỂ TRỐNG Ở ĐÂY

}