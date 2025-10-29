package myproject.todolist.service; // 1. "Địa chỉ" của file

// 2. Import các "Hợp đồng API" (DTOs)
import myproject.todolist.dto.CreateTaskDTO;
import myproject.todolist.dto.TaskDTO;
import myproject.todolist.exception.ResourceNotFoundException;

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

    /**
     * Lấy thông tin chi tiết của một Task bằng ID.
     *
     * @param id ID của Task cần tìm.
     * @return DTO của Task được tìm thấy.
     * @throws ResourceNotFoundException Khi không tìm thấy Task với ID tương ứng.
     */
    TaskDTO getTaskById(Long id);

    /**
     * Nghiệp vụ 4: Cập nhật một Task.
     * Sẽ tìm Task theo ID, nếu thấy, sẽ cập nhật nội dung.
     * @param id ID của Task cần cập nhật.
     * @param taskDetails DTO chứa thông tin mới (title, isCompleted).
     * @return Task đã được cập nhật (dưới dạng DTO).
     * @throws ResourceNotFoundException Khi không tìm thấy Task với ID tương ứng.
     */
    TaskDTO updateTask(Long id, TaskDTO taskDetails);
}