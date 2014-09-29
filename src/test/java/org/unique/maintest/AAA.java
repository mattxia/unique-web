package org.unique.maintest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.unique.common.tools.DateUtil;

public class AAA {

	public static void main(String[] args) {
		Object[] a = {1,2,3};
		List<Object> list = new ArrayList<Object>(a.length + 2);
		list.addAll(Arrays.asList(a));
		list.add(333);
		list.add(334);
		System.out.println(list);
		System.out.println(DateUtil.getCurrentTime());
	}
}
