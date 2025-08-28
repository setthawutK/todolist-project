package com.example.WORKPLS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor   // ✅ generate constructor(success, message, username)
@NoArgsConstructor    // ✅ generate default constructor (ไม่มี args)
public class ApiResponse {
    private boolean success;
    private String message;
    private String username;
}
