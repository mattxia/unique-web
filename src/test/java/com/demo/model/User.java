package com.demo.model;

import org.unique.plugin.dao.Model;


public class User extends Model<User>{

    private static final long serialVersionUID = -1496693714751372133L;
    
    public static User db = new User();
    
    private int uid;
    private String login_name;
    private String pass_word;
    private String user_name;
    private int is_admin;
    private int status;
    
    public int getUid() {
        return uid;
    }
    
    public void setUid(int uid) {
        this.uid = uid;
    }
    
    public String getLogin_name() {
        return login_name;
    }
    
    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }
    
    public String getPass_word() {
        return pass_word;
    }
    
    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }
    
    public String getUser_name() {
        return user_name;
    }
    
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    public int getIs_admin() {
        return is_admin;
    }
    
    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
}
