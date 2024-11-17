package com.App.SubmissionPortal.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Assignment")
public class Assignment {


   @Id
    private String id;
    private String userId;
    private String task;
    private String adminId;
    private String status;
    private LocalDateTime timeStamp;

    public Assignment() {
    }

    public Assignment(String id, String userId, String task, String adminId, String status, LocalDateTime timeStamp) {
        this.id = id;
        this.userId = userId;
        this.task = task;
        this.adminId = adminId;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
