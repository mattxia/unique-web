package com.demo.service.impl;

import java.util.List;

import org.unique.ioc.annotation.Service;

import com.demo.model.User;
import com.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Override
    public List<User> getUserList() {
        return User.db.findList("select * from user");
    }

    @Override
    public boolean deleteUser(int uid) {
        return User.db.delete("update user t set t.status = 0 where t.uid = ?", uid) > 0;
    }

}
