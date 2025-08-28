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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class CreateandUpdateList {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PatchMapping("/updateUp")
    public ResponseEntity<ApiResponse> updateUp(HttpServletRequest request,
                                                @RequestBody DairyInfor dairyinfor) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Missing or invalid Authorization header", null, null));
        }

        String username;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.key)
                    .build()
                    .parseClaimsJws(authHeader)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Invalid or expired token", null, null));
        }

        String orderID = dairyinfor.getOrderID();
        String newDailyInfo = dairyinfor.getDairy_info();
        Boolean newCheck = dairyinfor.isCheck();

        String checkSql = "SELECT COUNT(*) FROM listinfo WHERE order_ID = ? AND username = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, orderID, username);

        if (count == null || count == 0) {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Not found: This task doesn't belong to this user or doesn't exist", username, orderID));
        }

        String getSql = "SELECT dairy_info, boolean_check FROM listinfo WHERE order_ID = ? AND username = ?";
        Map<String, Object> currentRow = jdbcTemplate.queryForMap(getSql, orderID, username);

        String finalDailyInfo = (newDailyInfo != null && !newDailyInfo.isEmpty())
                ? newDailyInfo
                : (String) currentRow.get("dairy_info");

        Boolean finalCheck = (newCheck != null)
                ? newCheck
                : (Boolean) currentRow.get("boolean_check");

        String timestamp = new SimpleDateFormat("dd/MM/yyyy h:mm a").format(new Date());

        String updateSql = "UPDATE listinfo SET dairy_info = ?, time_stamp = ?, boolean_check = ? WHERE order_ID = ? AND username = ?";
        int rows = jdbcTemplate.update(updateSql, finalDailyInfo, timestamp, finalCheck, orderID, username);

        if (rows > 0) {
            return ResponseEntity.ok(new ApiResponse(true, "Update successful", username, orderID));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse(false, "Update failed", username, orderID));
        }
    }

    @PostMapping("/create-list")
    public ResponseEntity<ApiResponse> createList(HttpServletRequest request,
                                                  @RequestBody DairyInfor dairy) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Missing or invalid Authorization header", null, null));
        }

        String username;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.key)
                    .build()
                    .parseClaimsJws(authHeader)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Invalid or expired token", null, null));
        }

        String daily_info = dairy.getDairy_info();
        String timestamp = new SimpleDateFormat("dd/MM/yyyy h:mm a").format(new Date());
        boolean check = false;
        String orderID = String.valueOf(Math.round(Math.random() * 100000));

        String SQLInsertList = "INSERT INTO listinfo (dairy_info, time_stamp, boolean_check, username, order_id) VALUES (?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(SQLInsertList, daily_info, timestamp, check, username, orderID);

        if (rows > 0) {
            return ResponseEntity.ok(new ApiResponse(true, "Dairy info saved successfully", username, orderID));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse(false, "Failed to save info", username, orderID));
        }
    }
}
