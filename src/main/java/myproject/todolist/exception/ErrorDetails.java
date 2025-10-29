package myproject.todolist.exception;

import lombok.Data;
import lombok.NoArgsConstructor; // Rất quan trọng
import java.util.Date;

@Data // Tự tạo Getter, Setter...
@NoArgsConstructor // Tự tạo constructor rỗng (BẮT BUỘC)
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;

    // Giữ nguyên constructor 3 tham số của bạn
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}