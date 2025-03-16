package com.demo.model;

import java.time.LocalDateTime;

public class Course {
    private int courseId;
    private int instructorId;
    private String title;
    private String description;
    private double price;
    private String category;
    private LocalDateTime createdAt;
    private boolean isApproved;

    // Default Constructor
    public Course() {}

    // Parameterized Constructor
    public Course(int courseId, int instructorId, String title, String description, 
                  double price, String category, LocalDateTime createdAt, boolean isApproved) {
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
        this.isApproved = isApproved;
    }

    // Getters and Setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getInstructorId() { return instructorId; }
    public void setInstructorId(int instructorId) { this.instructorId = instructorId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

    // toString() Method for Debugging
    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", instructorId=" + instructorId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", isApproved=" + isApproved +
                '}';
    }
}

