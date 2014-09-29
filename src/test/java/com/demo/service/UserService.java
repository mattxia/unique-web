package com.demo.service;

import org.unique.plugin.dao.Page;

import com.demo.model.User;


public interface UserService {

	Page<User> getUserList();
    
    boolean deleteUser(int uid);
    
    User get(Integer uid);
}
