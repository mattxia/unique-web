package org.unique.test;

import org.junit.Test;
import org.unique.plugin.dao.Page;

import com.demo.model.User;

public class ModelTest {

	@Test
	public void testPage(){
		Page<User> page = User.db.findListPage(1, 10, "select * from t_user order by uid");
		System.out.println("当前页：" + page.getPage());
		System.out.println("总条数：" + page.getTotalCount());
		System.out.println("总页数：" + page.getTotalPage());
		System.out.println("下一页：" + page.getNext_page());
		System.out.println("上一页：" + page.getPrev_page());
		System.out.println("查询出的条数：" + page.getResults().size());
	}
}
