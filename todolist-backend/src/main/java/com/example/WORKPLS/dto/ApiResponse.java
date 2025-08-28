package com.example.WORKPLS.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private String username;

    // ✅ constructor มี args
    public ApiResponse(boolean success, String message, String username) {
        this.success = success;
        this.message = message;
        this.username = username;
    }

    // ✅ constructor เปล่า
    public ApiResponse() {}

    // ✅ getters & setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
