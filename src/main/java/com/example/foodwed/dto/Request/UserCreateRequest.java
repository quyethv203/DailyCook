package com.example.foodwed.dto.Request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCreateRequest {
    private String fullname;
    private String password;
    @Email(message = "USSER_Email")
    private String email;
}
