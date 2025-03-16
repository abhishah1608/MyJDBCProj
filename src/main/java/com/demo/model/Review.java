package com.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
	
    private UUID reviewId;  // GUID equivalent in Java
    private int courseId;
    private int studentId;
    private int rating;
    private String comment;
    private LocalDateTime reviewDate;

    // Default Constructor
    public Review() {}

    // Parameterized Constructor
    public Review(UUID reviewId, int courseId, int studentId, int rating, String comment, LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Getters and Setters
    public UUID getReviewId() { return reviewId; }
    public void setReviewId(UUID reviewId) { this.reviewId = reviewId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }

    // toString() Method for Debugging
    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
