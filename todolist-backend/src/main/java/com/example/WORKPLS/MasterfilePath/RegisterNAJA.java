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
@Repository
@RestController
public class RegisterNAJA {
    @Autowired
    private  JdbcTemplate jdbcTemplate; /*Lib ที่ช่วยQuery*/
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



/*Register*/
//@PostMapping("/RegisterNaja")
//@Transactional
//@ResponseBody
//    public ResponseEntity<String> RegisterMethod(
//            @RequestBody Register info_forRegis){
//    String register_username = BaseIndegredient.getUsername();
//    String register_password = BaseIndegredient.getPassword();
//    var SQL="SELECT * FROM Member"+"INSERT INTO Username(register_username) "+"INSERT INTO Password(register_password)";
//    Register querryfor_Regist = jdbcTemplate.queryForObject(SQL,new  BeanPropertyRowMapper<>(Register.class),register_username,register_password);
//    String finsihed ="Registed";
//    return ResponseEntity.ok("success");
@PostMapping("/RegisterNaja")
@Transactional
@ResponseBody
public ResponseEntity<String> registerMethod(@RequestBody Register info_forRegis) {

    String register_username = info_forRegis.getName();
    String register_password = info_forRegis.getPassword();


    String checkSql = "SELECT COUNT(*) FROM Member WHERE username = ?";
    Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, register_username);
    if (count != null && count > 0) {
        return ResponseEntity.badRequest().body("Username already exists");
    }


    String insertSql = "INSERT INTO Member (username, password) VALUES (?, ?)";
    int rows = jdbcTemplate.update(insertSql, register_username, register_password);
//    String Sql = "INSERT INTO ListInfo (username) VALUES (?)";
//    int row = jdbcTemplate.update(Sql, register_username);

    if (rows > 0 /*&& row>0*/) {
        return ResponseEntity.ok("Registered successfully");
    } else {
        return ResponseEntity.status(500).body("Registration failed");
    }



}




    }







