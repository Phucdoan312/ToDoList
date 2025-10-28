package myproject.todolist.service; // 1. "Địa chỉ" của file

// 2. Import các "Hợp đồng API" (DTOs)
import myproject.todolist.dto.CreateTaskDTO;
import myproject.todolist.dto.TaskDTO;
import java.util.List;

// 3. Đây là "Hợp đồng Logic" (Interface)
public interface TaskService {

    /**
     * Nghiệp vụ 1: Tạo một task mới.
     * @param createTaskDTO Dữ liệu đầu vào từ API
     * @return Task đã được tạo (dưới dạng DTO)
     */
    TaskDTO createTask(CreateTaskDTO createTaskDTO); // 4.

    /**
     * Nghiệp vụ 2: Lấy tất cả các task.
     * @return Danh sách tất cả các task (dưới dạng DTO)
     */
    List<TaskDTO> getAllTasks(); // 5.

}