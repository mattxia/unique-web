package com.demo.service.impl;

import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;

import com.demo.model.User;
import com.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Override
    public Page<User> getUserList() {
    	
        return User.db.findListPage(1, 10, "select * from t_user");
    }

    @Override
    public boolean deleteUser(int uid) {
        return User.db.delete("update user t set t.status = 0 where t.uid = ?", uid) > 0;
    }

	@Override
	public User get(Integer uid) {
		return User.db.findByCache("select * from t_user where uid = ?", uid);
	}

}
