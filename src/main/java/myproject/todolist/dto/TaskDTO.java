package myproject.todolist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Tự tạo Getter, Setter...
@NoArgsConstructor // Tự tạo Constructor rỗng
public class TaskDTO {

    // 1. Định nghĩa chính xác những gì server TRẢ VỀ
    private Long id;
    private String title;
    private boolean isCompleted;
}