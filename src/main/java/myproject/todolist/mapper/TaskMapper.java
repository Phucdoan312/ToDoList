package myproject.todolist.mapper;

// Import 3 "ngôn ngữ" mà chúng ta cần dịch
import myproject.todolist.dto.CreateTaskDTO;
import myproject.todolist.dto.TaskDTO;
import myproject.todolist.model.Task;

// Import "công cụ" của MapStruct
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// 1. @Mapper: Báo cho MapStruct biết "Đây là 1 file Mapper"
// 2. componentModel = "spring": DÒNG QUAN TRỌNG NHẤT
//    - Nó bảo MapStruct: "Hãy tạo ra 1 class thực thi (@Service, @Component...)
//    - ...để Spring Boot có thể @Autowired (tiêm) nó vào TaskServiceImpl."
@Mapper(componentModel = "spring")
public interface TaskMapper {

    // --- Hợp đồng Dịch 1: (Dịch từ CSDL -> API cho React) ---
    //
    // Mục đích: Dịch 1 Task (Entity) lấy từ CSDL
    //          thành 1 TaskDTO (an toàn) để trả về cho React.
    //
    // MapStruct tự động thấy `id`, `title`, `isCompleted`
    // có tên y hệt nhau ở cả 2 class nên nó tự động map 1:1.
    TaskDTO toTaskDTO(Task task);


    // --- Hợp đồng Dịch 2: (Dịch từ API của React -> CSDL) ---
    //
    // Mục đích: Dịch 1 CreateTaskDTO (React gửi lên)
    //          thành 1 Task (Entity) để chuẩn bị lưu vào CSDL.
    //
    // Vì 2 class này có trường KHÁC NHAU, ta phải chỉ rõ:

    // 3. @Mapping: "Hãy ánh xạ (map) trường `target` (ĐÍCH) là 'id'..."
    //    "... bằng cách `ignore = true` (LỜ NÓ ĐI)."
    //    Lý do: `CreateTaskDTO` không có `id`, CSDL sẽ tự tạo `id`.
    @Mapping(target = "id", ignore = true)

    // 4. @Mapping: "Hãy ánh xạ (map) trường `target` (ĐÍCH) là 'completed'..."
    //    "... bằng một giá trị `constant = "false"` (HẰNG SỐ)."
    //    Lý do: Khi tạo 1 task mới, nó *luôn luôn* chưa hoàn thành.
    @Mapping(target = "completed", constant = "false")
    Task toTask(CreateTaskDTO createTaskDTO);


    // --- Hợp đồng Dịch 3: (Dịch 1 danh sách) ---
    //
    // Mục đích: Dịch 1 List<Task> (danh sách từ CSDL)
    //          thành 1 List<TaskDTO> (danh sách cho React).
    //
    // MapStruct đủ thông minh để tự gọi hàm `toTaskDTO` (ở trên)
    // cho từng phần tử trong danh sách.
    List<TaskDTO> toTaskDTOList(List<Task> tasks);

}