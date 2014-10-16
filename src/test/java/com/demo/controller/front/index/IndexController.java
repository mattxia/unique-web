package com.demo.controller.front.index;

import org.unique.common.tools.JSONUtil;
import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.cache.Cache;
import org.unique.plugin.cache.JedisCache;
import org.unique.plugin.dao.Page;
import org.unique.web.annotation.Path;
import org.unique.web.core.Controller;
import org.unique.web.render.impl.PatchcaRender;

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
		Page<User> userList = userService.getUserList();
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
		this.render(new PatchcaRender());
	}
	
	@org.unique.web.annotation.Action("ff/show/{mid}")
	public void show() {
		Integer uid = this.getParaToInt();
		User user = userService.get(uid);
//		List<User> userList = userService.getList(null, 1, null);
//		boolean flag = userService.update(2, "qq", 1);
		System.out.println("show：" + user.getLogin_name());
	}
}
