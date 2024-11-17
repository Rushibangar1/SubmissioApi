package com.App.SubmissionPortal.Dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginDto {

    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;


    public LoginDto() {
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is mandatory") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is mandatory") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is mandatory") String password) {
        this.password = password;
    }
}
