package com.demo.model;

import java.time.LocalDateTime;

import com.demo.model.annotations.AutoIncrementField;

public class User {
	
	@AutoIncrementField
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;
    private String userName;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(int userId, String firstName, String lastName, String email, 
                String passwordHash, String role, LocalDateTime createdAt, 
                boolean isActive, String userName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.isActive = isActive;
        this.userName = userName;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    // toString() Method for Debugging
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", isActive=" + isActive +
                ", userName='" + userName + '\'' +
                '}';
    }
}
