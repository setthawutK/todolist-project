package com.example.WORKPLS.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private String username;
    private String orderId; // ✅ เพิ่ม field นี้

    // ✅ constructor มี args (4 arguments)
    public ApiResponse(boolean success, String message, String username, String orderId) {
        this.success = success;
        this.message = message;
        this.username = username;
        this.orderId = orderId;
    }

    // ✅ constructor มี args (3 arguments) เผื่อ Register ใช้
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

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
}
