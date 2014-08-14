package com.demo.service;

import java.util.List;

import com.demo.model.User;


public interface UserService {

    List<User> getUserList();
    
    boolean deleteUser(int uid);
    
}
