package com.example.WORKPLS.MasterfilePath;


import com.example.WORKPLS.Entity.DairyInfor;
import com.example.WORKPLS.Entity.Register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/RegisterNaja")
    @Transactional
    public ResponseEntity<ApiResponse> registerMethod(@RequestBody Register info_forRegis) {

        String register_username = info_forRegis.getName();
        String register_password = info_forRegis.getPassword();

        // ✅ ตรวจสอบว่ามี username ซ้ำหรือไม่
        String checkSql = "SELECT COUNT(*) FROM Member WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, register_username);

        if (count != null && count > 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Username already exists", null));
        }

        // ✅ Insert user
        String insertSql = "INSERT INTO Member (username, password) VALUES (?, ?)";
        int rows = jdbcTemplate.update(insertSql, register_username, register_password);

        if (rows > 0) {
            return ResponseEntity.ok(
                    new ApiResponse(true, "Registered successfully", register_username)
            );
        } else {
            return ResponseEntity.status(500).body(
                    new ApiResponse(false, "Registration failed", null)
            );
        }
    }
}







