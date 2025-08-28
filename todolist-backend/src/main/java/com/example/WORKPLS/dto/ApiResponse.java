package com.example.WORKPLS.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private String username;
    private String orderId;
    private Object data;   // ✅ ฟิลด์เสริม

    // ✅ constructor full args
    public ApiResponse(boolean success, String message, String username, String orderId, Object data) {
        this.success = success;
        this.message = message;
        this.username = username;
        this.orderId = orderId;
        this.data = data;
    }

    // ✅ constructor partial args (เผื่อใช้กับ Register ที่ไม่ต้องมี orderId)
    public ApiResponse(boolean success, String message, String username) {
        this(success, message, username, null, null);
    }

    // ✅ constructor no args
    public ApiResponse() {}

    // getters & setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
