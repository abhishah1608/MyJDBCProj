package com.demo.model;

import java.time.LocalDateTime;

public class Enrollment {
    private int enrollmentId;
    private int courseId;
    private int studentId;
    private LocalDateTime enrollmentDate;
    private String paymentStatus;

    // Default Constructor
    public Enrollment() {}

    // Parameterized Constructor
    public Enrollment(int enrollmentId, int courseId, int studentId, LocalDateTime enrollmentDate, String paymentStatus) {
        this.enrollmentId = enrollmentId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.enrollmentDate = enrollmentDate;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    // toString() Method for Debugging
    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", enrollmentDate=" + enrollmentDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
