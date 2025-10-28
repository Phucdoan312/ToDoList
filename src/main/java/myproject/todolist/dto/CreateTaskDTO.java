package myproject.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Tự tạo Getter, Setter...
@NoArgsConstructor // Tự tạo Constructor rỗng
public class CreateTaskDTO {

    // 1. Chỉ chứa 1 trường duy nhất mà React được phép gửi lên
    private String title;
}