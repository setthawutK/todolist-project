package com.example.WORKPLS.MasterfilePath;
import com.example.WORKPLS.Entity.DairyInfor;
import com.example.WORKPLS.Entity.Register;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class SessionAndLogin {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Register member) {
        String SQLCheckMember = "SELECT COUNT(*) FROM Member WHERE username = ? AND password = ?";
        String username = member.getName();
        String password = member.getPassword();

        Integer count = jdbcTemplate.queryForObject(SQLCheckMember, Integer.class, username, password);

        if (count != null && count > 0) {
            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .signWith(JwtUtil.key)   // ใช้ key จาก JwtUtil
                    .compact();

            // ✅ return JSON { "accessToken": token }
            Map<String, String> response = Map.of("accessToken", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Invalid login");
    }
    
    @GetMapping("/loginFinished/showlist")
    public ResponseEntity<?> showList(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null /*|| !authHeader.startsWith("Bearer ")*/) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }


        String jwt = authHeader;
        String username;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.key)  // ใช้ key จาก JwtUtil
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        System.out.println(username);

        // ตรวจสอบว่ามี user จริงหรือไม่
        String SQLCheckMember = "SELECT COUNT(*) FROM member WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(SQLCheckMember, Integer.class, username);

        if (count == null || count == 0) {
            return ResponseEntity.status(404).body("User not found");
        }

        // ดึงข้อมูลจาก InfoList ตาม username
        String SQLGetInfoList = "SELECT * FROM listinfo WHERE username = ?";
        List<Map<String, Object>> infoList = jdbcTemplate.queryForList(SQLGetInfoList, username);

        return ResponseEntity.ok(infoList);



    }
    @GetMapping("/loginFinished/showlist/filter")
    public ResponseEntity<?> showListFiltered(HttpServletRequest request,
                                              @RequestHeader("keyword") String keyword) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null /*|| !authHeader.startsWith("Bearer ")*/) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String jwt = authHeader;
        String username;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.key)  // ✅ ใช้ key เดียวกับตอน login
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        System.out.println("User: " + username);

        // ตรวจสอบ user
        String SQLCheckMember = "SELECT COUNT(*) FROM member WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(SQLCheckMember, Integer.class, username);

        if (count == null || count == 0) {
            return ResponseEntity.status(404).body("User not found");
        }

        // ดึงข้อมูลที่ filter ตาม keyword
        String SQLGetInfoList = "SELECT * FROM listinfo WHERE username = ? AND dairy_info LIKE ?";
        List<Map<String, Object>> filteredList = jdbcTemplate.queryForList(SQLGetInfoList, username, "%" + keyword + "%");

        return ResponseEntity.ok(filteredList);
    }





//    @GetMapping("/loginFinished/showlist")
//    public ResponseEntity<?> showList(@RequestBody Register member) {
//        String membername = member.getName();
//
//
//        String SQLCheckMember = "SELECT COUNT(*) FROM Member WHERE username = ?";
//        Integer count = jdbcTemplate.queryForObject(SQLCheckMember, Integer.class, membername);
//
//        if (count == null || count == 0) {
//            return ResponseEntity.status(404).body("User not found");
//        }
//
//
//        String SQLGetInfoList = "SELECT * FROM InfoList WHERE username = ?";
//        List<Map<String, Object>> infoList = jdbcTemplate.queryForList(SQLGetInfoList, membername);
//
//        return ResponseEntity.ok(infoList);}
/*not essential to use*/
//    @GetMapping("/loginFinished")
//    public String me(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String jwt = authHeader.substring(7);
//            String username = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(jwt)
//                    .getBody()
//                    .getSubject();
//
//            return "Hello " + username;
//        }
//        return "Not logged in";
//    }


}
