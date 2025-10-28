package myproject.todolist.controller; // 1. "Địa chỉ"

// 2. Import "Hợp đồng API" (DTOs) và "Hợp đồng Logic" (Service)
import myproject.todolist.dto.CreateTaskDTO;
import myproject.todolist.dto.TaskDTO;
import myproject.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; // Import các annotation Web

import java.util.List;

// 3. BÁO CHO SPRING: Đây là API Controller, trả về JSON
@RestController
// 4. ĐỊA CHỈ GỐC: Tất cả API trong này đều bắt đầu bằng /api/tasks
@RequestMapping("/api/tasks")
// 5. CHO PHÉP REACT (cổng 5173) gọi API
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    // 6. Tiêm (inject) "Hợp đồng Logic" (Interface)
    @Autowired
    private TaskService taskService; // Chỉ biết "Cái Menu"

    // 7. API 1: TẠO TASK MỚI
    @PostMapping // "Bắt" lấy request POST (tạo mới)
    public TaskDTO createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        // 8. Chuyển lệnh cho "Bộ não"
        return taskService.createTask(createTaskDTO);
    }

    // 9. API 2: LẤY TẤT CẢ TASK
    @GetMapping // "Bắt" lấy request GET (lấy dữ liệu)
    public List<TaskDTO> getAllTasks() {
        // 10. Chuyển lệnh cho "Bộ não"
        return taskService.getAllTasks();
    }
}