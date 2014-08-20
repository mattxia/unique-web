package org.unique.test;

import java.util.List;

import org.junit.Test;

import com.demo.model.User;

public class ModelTest {

	@Test
	public void testPage(){
		List<User> list = User.db.findListPage(1, 10, "select * from t_user");
		System.out.println(list.size());
	}
}
