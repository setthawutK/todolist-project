package com.example.WORKPLS.MasterfilePath;

import com.example.WORKPLS.Entity.DairyInfor;
import com.example.WORKPLS.dto.ApiResponse;


import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
public class DELETER {
    @Autowired
    public JdbcTemplate jdbcTemplate;


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(HttpServletRequest request,
                                             @RequestHeader("orderID") String orderID) {
        /* 1. ดึง Authorization header*/
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String jwt = authHeader;
        String username;
        try {
            //// 2. ถอดรหัส JWT → username*/
            username = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // *3. ลบจาก DB ตาม orderID + username*/
        String SQL = "DELETE FROM ListInfo WHERE order_ID = ? AND username = ?";
        int rowsAffected = jdbcTemplate.update(SQL, orderID, username);

        if (rowsAffected > 0) {
            return ResponseEntity.ok(
                new ApiResponse(true, "Deleted successfully", username, orderId, null)
            );
        } else {
            return ResponseEntity.status(404).body(
                new ApiResponse(false, "No matching record found", username, orderId, null)
            );
        }

    }
}


