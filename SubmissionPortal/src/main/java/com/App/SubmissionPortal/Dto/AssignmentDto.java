package com.App.SubmissionPortal.Dto;

import jakarta.validation.constraints.NotBlank;

public class AssignmentDto {

    @NotBlank(message = "User ID is mandatory")
    private String userId;

    @NotBlank(message = "Task is mandatory")
    private String task;

    private String status;


    public AssignmentDto() {
    }

    public AssignmentDto(String userId, String task, String status) {
        this.userId = userId;
        this.task = task;
        this.status = status;
    }

    public @NotBlank(message = "User ID is mandatory") String getUserId() {
        return userId;
    }

    public void setUserId(@NotBlank(message = "User ID is mandatory") String userId) {
        this.userId = userId;
    }

    public @NotBlank(message = "Task is mandatory") String getTask() {
        return task;
    }

    public void setTask(@NotBlank(message = "Task is mandatory") String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "AssignmentDto{" +
                "userId='" + userId + '\'' +
                ", task='" + task + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
