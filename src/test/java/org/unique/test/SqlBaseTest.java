package org.unique.test;

import org.unique.plugin.dao.SqlBase;

public class SqlBaseTest {

	public static void main(String[] args) {
		SqlBase base = SqlBase.select("select * from t_user u");
		base.likeLeft("u.username", null).likeLeft("u.email", null).eq("u.status", null).order("u." + null);
		System.out.println(base.getSQL());
		
		
		SqlBase delete = SqlBase.delete("delete from t_post");
		delete.eq("pid", "123");
		delete.likeLeft("name", "jack");
		System.out.println("delete : " + delete.getSQL());
		System.out.println("params : " + delete.getParams().length);

		SqlBase update = SqlBase.update("update t_post");
		update.set("name", "jack");
		update.set("last_time", 1234546);
		update.set("is_pub", null);
		update.eq("pid", 123);
		update.eq("is_pub", 0);
		System.out.println("update : " + update.getSQL());
		System.out.println("params : " + update.getParams().length);

		SqlBase select = SqlBase.select("select * from t_post");
		select.eq("pid", "123");
		select.eq("is_pub", null);
		select.gt("age", 18);
		select.likeLeft("name", "jack");
		System.out.println("select : " + select.getSQL());
		System.out.println("params : " + select.getParams().length);
		
		
	}
}
