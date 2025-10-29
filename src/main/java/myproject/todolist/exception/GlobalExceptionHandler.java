package myproject.todolist.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

// Báo Spring: "Đây là lớp 'cố vấn' đặc biệt, hãy 'nghe lén' tất cả Controller"
@ControllerAdvice
public class GlobalExceptionHandler {

    // Báo Spring: "Nếu có ai văng ra lỗi ResourceNotFoundException..."
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // ...hãy "bắt" nó lại và trả về JSON "đẹp" này thay vì crash"
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    // Bạn cũng có thể thêm các @ExceptionHandler khác cho các lỗi khác ở đây
}