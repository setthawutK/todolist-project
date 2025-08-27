package com.example.WORKPLS.MasterfilePath;

import com.example.WORKPLS.Entity.DairyInfor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class CreateandUpdateList {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @PatchMapping("/updateUp")
    public ResponseEntity<String> updateUp(HttpServletRequest request,
                                           @RequestBody DairyInfor dairyinfor) {
        // 1. ดึง token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String jwt = authHeader;
        String username;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        String orderID = dairyinfor.getOrderID();
        String newDailyInfo = dairyinfor.getDairy_info();
        Boolean newCheck = dairyinfor.isCheck();
        System.out.println(orderID);
        System.out.println(newDailyInfo);

        // ตรวจสอบว่ามี record หรือไม่
        String checkSql = "SELECT COUNT(*) FROM listinfo WHERE order_ID = ? AND username = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, orderID, username);

        if (count == null || count == 0) {
            return ResponseEntity.status(404).body("Not found: This task doesn't belong to this user or doesn't exist");
        }

        // ดึงค่าปัจจุบัน
        String getSql = "SELECT dairy_info, boolean_check FROM listinfo WHERE order_ID = ? AND username = ?";
        Map<String, Object> currentRow = jdbcTemplate.queryForMap(getSql, orderID, username);

        // ถ้า newDailyInfo ไม่ได้ส่งมา → ใช้ของเก่า
        String finalDailyInfo = (newDailyInfo != null && !newDailyInfo.isEmpty())
                ? newDailyInfo
                : (String) currentRow.get("dairy_info");

        // ถ้า newCheck ไม่ได้ส่งมา → ใช้ของเก่า
        Boolean finalCheck = (newCheck != null)
                ? newCheck
                : (Boolean) currentRow.get("boolean_check");

        // timestamp ใหม่
        String timestamp = new SimpleDateFormat("dd/MM/yyyy h:mm a").format(new Date());

        // update
        String updateSql = "UPDATE listinfo SET dairy_info = ?, time_stamp = ?, boolean_check = ? WHERE order_ID = ? AND username = ?";
        int rows = jdbcTemplate.update(updateSql, finalDailyInfo, timestamp, finalCheck, orderID, username);
        System.out.println(finalDailyInfo);
        if (rows > 0) {
            return ResponseEntity.ok("Update successful");
        } else {
            return ResponseEntity.status(500).body("Update failed");
        }
    }

//    @PatchMapping("/updateUp")
//    public ResponseEntity<String> updateUp(HttpServletRequest request,
//                                           @RequestBody DairyInfor dairyinfor) {
//        // 1. ดึง token
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null) {
//            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
//        }
//
//        String jwt = authHeader;
//        String username;
//        try {
//            username = Jwts.parserBuilder()
//                    .setSigningKey(JwtUtil.key)
//                    .build()
//                    .parseClaimsJws(jwt)
//                    .getBody()
//                    .getSubject();
//        } catch (Exception e) {
//            return ResponseEntity.status(401).body("Invalid or expired token");
//        }
//
//        String orderID = dairyinfor.getOrderID();
//        String newDailyInfo = dairyinfor.getDaily_info();
//        Boolean newCheck = dairyinfor.isCheck();
//
//        String checkSql = "SELECT COUNT(*) FROM listinfo WHERE order_ID = ? AND username = ?";
//        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, orderID, username);
//
//        if (count == null || count == 0) {
//            return ResponseEntity.status(404).body("Not found: This task doesn't belong to this user or doesn't exist");
//        }
//
//        String getSql = "SELECT dairy_info, boolean_check FROM listinfo WHERE order_ID = ? AND username = ?";
//        Map<String, Object> currentRow = jdbcTemplate.queryForMap(getSql, orderID, username);
////
////        String finalDailyInfo = (newDailyInfo != null && !newDailyInfo.isEmpty())
////                ? newDailyInfo
////                : (String) currentRow.get("dairy_info");
////
////        Boolean finalCheck = (newCheck != null)
////                ? newCheck
////                : (Boolean) currentRow.get("boolean_check");
//
//        String timestamp = new SimpleDateFormat("dd/MM/yyyy h:mm a").format(new Date());
//
//        String updateSql = "UPDATE listinfo SET dairy_info = ?, time_stamp = ?, boolean_check = ? WHERE order_ID = ? AND username = ?";
//        int rows = jdbcTemplate.update(updateSql, finalDailyInfo, timestamp, finalCheck, orderID, username);
//
//        if (rows > 0) {
//            return ResponseEntity.ok("Update successful");
//        } else {
//            return ResponseEntity.status(500).body("Update failed");
//        }
//    }

    @PostMapping("/create-list")
    public ResponseEntity<String> createList(HttpServletRequest request,
                                             @RequestBody DairyInfor dairy) {
        /*ดึง Authorization header*/
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String jwt = authHeader;
        String username;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.key)  /*ใช้ key จาก JwtUtil*/
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        System.out.println(username);

        /* รับข้อมูลจาก body*/
        String daily_info = dairy.getDairy_info();
        String timestamp = new SimpleDateFormat("dd/MM/yyyy h:mm a").format(new Date());
        boolean check = false;
        String orderID = String.valueOf(Math.random() * 100000);

        /*Insert ลงตาราง InfoList ของ user ที่ได้จาก token*/
        String SQLInsertList = "INSERT INTO listinfo (dairy_info, time_stamp, boolean_check, username, order_id) VALUES (?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(SQLInsertList, daily_info, timestamp, check, username, orderID);

        if (rows > 0) {
            return ResponseEntity.ok("Dairy info saved successfully for user: " + username);
        } else {
            return ResponseEntity.status(500).body("Failed to save info");
        }
    }


//    @PostMapping("/create-list")
//    public ResponseEntity<String> createList(@RequestBody Register member,
//                                             @RequestBody DairyInfor dairy) {
//        String daily_info = dairy.getDaily_info();
//        String username = member.getName();
//
//
//        String timestamp = new SimpleDateFormat("dd/MM/yyyy h:mm a").format(new Date());
//
//
//        boolean check = false;
//
//        String orderID = String.valueOf(Math.random()*100000);
//        String SQLInsertList = "INSERT INTO InfoList (dairy_info, time_stamp, boolean_check, username,orderID) VALUES (?, ?, ?, ?,?)";
//
//        int rows = jdbcTemplate.update(SQLInsertList, daily_info, timestamp, check, username,orderID);
//
//        if (rows > 0) {
//            return ResponseEntity.ok("Dairy info saved successfully");
//        } else {
//            return ResponseEntity.status(500).body("Failed to save info");
//        }
//    }
}






