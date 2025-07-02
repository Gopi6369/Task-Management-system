package com.example.Task1.Entity;

import jakarta.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String employee;
    private String deadline;
    private String date;
    private String status;
    private String rating;
    private String feedback;

    private String reassignedTo;    // Already used for new assignee
    private String reassignedFrom;  // ✅ Add this field to store who reassigned

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getEmployee() { return employee; }

    public void setEmployee(String employee) { this.employee = employee; }

    public String getDeadline() { return deadline; }

    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating; }

    public String getFeedback() { return feedback; }

    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getReassignedTo() { return reassignedTo; }

    public void setReassignedTo(String reassignedTo) { this.reassignedTo = reassignedTo; }

    public String getReassignedFrom() { return reassignedFrom; }

    public void setReassignedFrom(String reassignedFrom) { this.reassignedFrom = reassignedFrom; }
}
