package com.demo.service;

import java.util.List;

import org.unique.plugin.dao.Page;

import com.demo.model.User;


public interface UserService {

	Page<User> getUserList();
    
    boolean deleteUser(int uid);
    
    User get(Integer uid);
    
    boolean update(Integer uid, String nickName, Integer status);
    
    List<User> getList(String nickName, Integer is_admin, Integer status);
}
