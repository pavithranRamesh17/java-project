package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Complaint {
    private String complaintId;
    private String userId;
    private String citizenName;
    private String title;
    private String description;
    private String category;
    private String location;
    private String status; // "PENDING", "INVESTIGATING", "RESOLVED", "REJECTED"
    private String priority; // "LOW", "MEDIUM", "HIGH", "URGENT"
    private String assignedOfficer;
    private String remarks;
    private String imagePath;
    private String createdAt;
    private String updatedAt;

    public Complaint() {
        this.complaintId = UUID.randomUUID().toString();
        this.status = "PENDING";
        this.priority = "MEDIUM";
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.updatedAt = this.createdAt;
    }

    public Complaint(String userId, String citizenName, String title, String description,
                     String category, String location, String imagePath) {
        this();
        this.userId = userId;
        this.citizenName = citizenName;
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public String getComplaintId() { return complaintId; }
    public void setComplaintId(String complaintId) { this.complaintId = complaintId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCitizenName() { return citizenName; }
    public void setCitizenName(String citizenName) { this.citizenName = citizenName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getPriority() { return priority; }
    public void setPriority(String priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getAssignedOfficer() { return assignedOfficer; }
    public void setAssignedOfficer(String assignedOfficer) {
        this.assignedOfficer = assignedOfficer;
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return title + " - " + status + " (" + category + ")";
    }
}