package myproject.todolist.service; // 1. "Địa chỉ"

// 2. Import "Hợp đồng" và các "Công cụ"
import myproject.todolist.dto.CreateTaskDTO;
import myproject.todolist.dto.TaskDTO;
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
}