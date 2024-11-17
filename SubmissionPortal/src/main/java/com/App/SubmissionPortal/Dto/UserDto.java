package com.App.SubmissionPortal.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDto {


    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$", message = "Password must contain at least one letter and one number")
    private String password;

    @NotBlank(message = "Role is mandatory")
    private String role;


    public UserDto() {
    }

    public UserDto(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public @NotBlank(message = "Name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is mandatory") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$", message = "Password must contain at least one letter and one number") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is mandatory") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$", message = "Password must contain at least one letter and one number") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Role is mandatory") String getRole() {
        return role;
    }

    public void setRole(@NotBlank(message = "Role is mandatory") String role) {
        this.role = role;
    }
}
