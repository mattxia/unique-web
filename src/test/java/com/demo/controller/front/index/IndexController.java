package com.demo.controller.front.index;

import java.io.IOException;
import java.util.List;

import org.unique.common.tools.JSONUtil;
import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.cache.Cache;
import org.unique.plugin.cache.JedisCache;
import org.unique.plugin.patchca.PatchcaPlugin;
import org.unique.web.annotation.Path;
import org.unique.web.core.Controller;

import com.demo.model.User;
import com.demo.service.UserService;

@Path("/")
public class IndexController extends Controller {

	@Autowired
	UserService userService;

	public void login() {
		this.render("login");
	}

	public void index() {
		System.out.println("before");
		List<User> userList = userService.getUserList();
		System.out.println("aaa:" + JSONUtil.toJSON(userList));
		System.out.println("after");
		this.renderJson(JSONUtil.toJSON(userList));

		Cache redis = new JedisCache();
		String key = "user:floowing:100";

		redis.sadd("user:floowing:200", "100");
		redis.sadd("user:floowing:202", "100");
		redis.sadd("user:floowing:202", "205");

		redis.sadd(key, "200");
		redis.sadd("user:floow_by:200", "100");
		redis.sadd(key, "202");
		redis.sadd("user:floow_by:202", "100");
		redis.sadd(key, "203");
		redis.sadd("user:floow_by:203", "100");
		redis.sadd(key, "204");
		redis.sadd("user:floow_by:204", "100");
		redis.sadd(key, "205");
		redis.sadd("user:floow_by:205", "100");

		System.out.println("有" + redis.scard(key) + "关注");
		System.out.println("删除203" + redis.srem(key, "203"));
		System.out.println("有" + redis.scard(key) + "粉丝");

	}

	public void code() {
		try {
			PatchcaPlugin.crimg(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show() {
		
	}
}
