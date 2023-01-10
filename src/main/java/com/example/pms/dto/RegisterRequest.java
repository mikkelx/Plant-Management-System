package com.example.pms.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    @Pattern(regexp = "^(?:(?=.*[a-z]).*)[^\\s]{6,}$", message = "Username should contain minimum six characters")
    private String username;

//    @Pattern(regexp = "^[^0]+$", message="Plant must be choosen!")
    @Pattern(regexp = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*)[^\\s]{8,}$", message = "Password should contain minimum eight characters, at least one uppercase and lowercase letter and one number:")
    private String password;
    @Pattern(regexp = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*)[^\\s]{8,}$", message = "Password should contain minimum eight characters, at least one uppercase and lowercase letter and one number:")
    private String password_repeat;

    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public String getPassword_repeat() {
        return password_repeat;
    }

    public void setPassword_repeat(String password_repeat) {
        this.password_repeat = password_repeat;
    }
}
