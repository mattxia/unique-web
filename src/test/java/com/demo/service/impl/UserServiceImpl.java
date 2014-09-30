package com.demo.service.impl;

import java.util.List;

import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;
import org.unique.plugin.dao.SqlBase;

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
        return User.db.deleteByPK(uid) > 0;
    }

	@Override
	public User get(Integer uid) {
		return User.db.find("select t.* from t_user t where t.uid = ?", uid);
	}

	@Override
	public List<User> getList(String nickName, Integer is_admin, Integer status) {
		SqlBase sql = SqlBase.select("select * from t_user");
		sql.eq("nickName", nickName).eq("is_admin", is_admin).eq("status", status);
		return User.db.findList(sql.getSQL(), sql.getParams());
	}

	@Override
	public boolean update(Integer uid, String nickName, Integer status) {
		SqlBase sql = SqlBase.update("update t_user");
		sql.set("nickname", nickName).set("status", status).eq("uid", uid);
		return User.db.update(sql.getSQL(), uid, sql.getParams()) > 0;
	}

}
