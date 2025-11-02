package myproject.todolist.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String accessToken;
    private String tokenType = "Bearer "; // Chuẩn của JWT

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}