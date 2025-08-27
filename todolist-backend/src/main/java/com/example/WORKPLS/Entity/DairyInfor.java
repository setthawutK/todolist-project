package com.example.WORKPLS.Entity;


public class DairyInfor
{

    private String dairy_info;
    private boolean check;
    private String timeStamp;
    private String username;
    private String password;
    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public DairyInfor() {
    }

    public DairyInfor(String dairy_info, String orderID, boolean check) {
        this.dairy_info = dairy_info;
        this.orderID = orderID;
        this.check = check;
    }

    public DairyInfor(String dairy_info, boolean check, String username, String orderID) {
        this.dairy_info = dairy_info;
        this.check = check;
        this.username = username;
        this.orderID = orderID;
    }

    public DairyInfor(String dairy_info) {
        this.dairy_info = dairy_info;
    }

    public DairyInfor(String dairy_info, String username) {
        this.dairy_info = dairy_info;
        this.username = username;
    }

    public DairyInfor(String dairy_info, Boolean check, String username, String orderID, boolean check1) {
        this.dairy_info = dairy_info;
        this.check = check;
        this.username = username;

        this.orderID = orderID;
        this.check = check1;
    }

    public DairyInfor(String dairy_info, boolean check, String timeStamp, String username, String password) {
        this.dairy_info = dairy_info;
        this.check = check;
        this.timeStamp = timeStamp;
        this.username = username;
        this.password = password;
    }

    public String getDairy_info() {
        return dairy_info;
    }

    public void setDairy_info(String dairy_info) {
        this.dairy_info = dairy_info;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
