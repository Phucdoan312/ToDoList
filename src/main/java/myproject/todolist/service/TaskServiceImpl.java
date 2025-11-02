package myproject.todolist.service;

import myproject.todolist.dto.CreateTaskDTO;
import myproject.todolist.dto.TaskDTO;
import myproject.todolist.exception.ResourceNotFoundException;
import myproject.todolist.mapper.TaskMapper;
import myproject.todolist.model.Task;
import myproject.todolist.model.User;
import myproject.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor; // ⬅️ THÊM IMPORT
import org.springframework.security.access.AccessDeniedException; // ⬅️ THÊM IMPORT
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // 1. SỬA: Dùng Constructor Injection (Thay thế @Autowired)
public class TaskServiceImpl implements TaskService {

    // 2. SỬA: Dùng final và Tiêm (Inject) qua Constructor
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final AuthService authService; // ⬅️ THÊM DỊCH VỤ AUTH MỚI

    // 3. LOGIC HỖ TRỢ: Kiểm tra phân quyền
    private void checkTaskOwnership(Task task, Long currentUserId) {
        if (!task.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("Bạn không có quyền truy cập hoặc chỉnh sửa Task này.");
        }
    }

    // --- NGHIỆP VỤ (BUSINESS LOGIC) ---

    @Override
    public List<TaskDTO> getAllTasks() {
        // 4. LẤY USER ĐANG LOGIN
        User currentUser = authService.getCurrentUser();

        // 5. LỌC: Chỉ lấy Task của User này (findByUser_Id)
        List<Task> tasks = taskRepository.findByUser_Id(currentUser.getId());

        return taskMapper.toTaskDTOList(tasks);
    }

    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) {
        // 6. LẤY USER ĐANG LOGIN và GẮN TASK
        User currentUser = authService.getCurrentUser();

        Task taskToSave = taskMapper.toTask(createTaskDTO);
        taskToSave.setUser(currentUser); // ⬅️ GẮN USER

        Task savedTask = taskRepository.save(taskToSave);
        return taskMapper.toTaskDTO(savedTask);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Task với ID: " + id));

        // 7. PHÂN QUYỀN: Kiểm tra quyền xem Task
        checkTaskOwnership(task, authService.getCurrentUser().getId());

        return taskMapper.toTaskDTO(task);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDetails) {
        // B1: TÌM TASK
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không thể cập nhật Task. Không tìm thấy ID: " + id));

        // 8. PHÂN QUYỀN: KIỂM TRA QUYỀN TRƯỚC KHI SỬA
        checkTaskOwnership(taskToUpdate, authService.getCurrentUser().getId());

        // B2: CẬP NHẬT
        taskToUpdate.setTitle(taskDetails.getTitle());
        taskToUpdate.setCompleted(taskDetails.isCompleted());

        // B3: LƯU
        Task updatedTask = taskRepository.save(taskToUpdate);
        return taskMapper.toTaskDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        // B1: TÌM TASK
        Task taskToDelete = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không thể xóa Task. Không tìm thấy ID: " + id));

        // 9. PHÂN QUYỀN: KIỂM TRA QUYỀN TRƯỚC KHI XÓA
        checkTaskOwnership(taskToDelete, authService.getCurrentUser().getId());

        // B2: XÓA
        taskRepository.delete(taskToDelete);
    }
}
