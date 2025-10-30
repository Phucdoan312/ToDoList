package myproject.todolist.service; // 1. "Địa chỉ"

// 2. Import "Hợp đồng" và các "Công cụ"
import myproject.todolist.dto.CreateTaskDTO;
import myproject.todolist.dto.TaskDTO;
import myproject.todolist.exception.ResourceNotFoundException;
import myproject.todolist.mapper.TaskMapper;
import myproject.todolist.model.Task;
import myproject.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 3. BÁO CHO SPRING: "Đây là Bean Dịch vụ"
@Service
public class TaskServiceImpl implements TaskService { // 4. "Thực thi Hợp đồng"

    // 5. Tiêm (inject) các "cấp dưới"
    @Autowired
    private TaskRepository taskRepository; // "Thủ kho"

    @Autowired
    private TaskMapper taskMapper; // "Người phiên dịch"

    // 6. Logic Nghiệp vụ 1: LẤY TẤT CẢ TASK
    @Override
    public List<TaskDTO> getAllTasks() {
        // B1: Ra lệnh "Thủ kho" lấy tất cả Task (dạng Entity)
        List<Task> tasks = taskRepository.findAll();

        // B2: Ra lệnh "Phiên dịch" dịch List đó sang DTO
        return taskMapper.toTaskDTOList(tasks);
    }

    // 7. Logic Nghiệp vụ 2: TẠO TASK MỚI
    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) {
        // B1: Ra lệnh "Phiên dịch" dịch DTO (React) -> Entity (CSDL)
        //     (Lúc này taskToSave đã được set 'isCompleted=false' và 'id=null'
        //      nhờ TaskMapper của bạn)
        Task taskToSave = taskMapper.toTask(createTaskDTO);

        // B2: Ra lệnh "Thủ kho" lưu Entity đó vào CSDL
        //     (CSDL sẽ tự tạo ID cho nó)
        Task savedTask = taskRepository.save(taskToSave);

        // B3: Ra lệnh "Phiên dịch" dịch Entity (đã có ID) -> DTO
        //     để trả về cho React
        return taskMapper.toTaskDTO(savedTask);
    }

    // 8. Logic Nghiệp vụ 3: LẤY 1 TASK THEO ID
    @Override
    public TaskDTO getTaskById(Long id) {
        // B1: Ra lệnh "Thủ kho" tìm Task theo ID
        Task task = taskRepository.findById(id)
                // B2: Nếu "Thủ kho" không tìm thấy, "văng" lỗi 404
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Task với ID: " + id));

        // B3: Nếu tìm thấy, ra lệnh "Phiên dịch" dịch Entity -> DTO
        return taskMapper.toTaskDTO(task);
    }

    // 9. Logic Nghiệp vụ 4: CẬP NHẬT 1 TASK
    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDetails) {
        // 🧠 B1: TÌM (Find) - Tái sử dụng logic "tìm hoặc văng lỗi"
        //      Lấy Task (dạng Entity) đang tồn tại trong CSDL.
        //      Chúng ta phải lấy ra trước, vì chúng ta cần "sửa" nó.
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không thể cập nhật Task. Không tìm thấy ID: " + id));

        // ✍️ B2: CẬP NHẬT (Update) - Áp dụng thay đổi
        //      Lấy thông tin mới từ 'taskDetails' (DTO)
        //      và "đè" (set) nó vào 'taskToUpdate' (Entity).
        taskToUpdate.setTitle(taskDetails.getTitle());
        taskToUpdate.setCompleted(taskDetails.isCompleted());

        // 💾 B3: LƯU (Save) - Gửi lại cho "Thủ kho"
        //      Ra lệnh "Thủ kho" lưu lại Entity đã bị thay đổi.
        //      JPA đủ thông minh để biết task này "đã có ID",
        //      nên nó sẽ chạy lệnh SQL "UPDATE" thay vì "INSERT".
        Task updatedTask = taskRepository.save(taskToUpdate);

        // 📤 B4: DỊCH (Map) - Trả kết quả về cho React
        //      Ra lệnh "Phiên dịch" dịch Entity (đã cập nhật) -> DTO.
        return taskMapper.toTaskDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        // B1: TÌM (Find) - Tái sử dụng logic "tìm hoặc văng lỗi"
        Task taskToDelete = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không thể xóa Task. Không tìm thấy ID: " + id));

        // B2: XÓA (Delete) - Ra lệnh "Thủ kho"
        //     Gửi Entity tìm được để xóa.
        taskRepository.delete(taskToDelete);

        // (Không cần B3, vì hàm này là "void", không trả về gì)
    }
}