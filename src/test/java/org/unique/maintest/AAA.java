package org.unique.maintest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AAA {

	public static void main(String[] args) {
		String url = "/admin/pic/3/2/2";
		Pattern p = Pattern.compile("[/\\w]?[\\d]+");
		Matcher m = p.matcher(url);
		List<String> pp = new ArrayList<String>();
		while(m.find()){
			String mg = m.group().replaceAll("/", "");
			url = url.replace(mg, "{#}");
			System.out.println(Integer.valueOf(mg));
		}
		System.out.println(url);
	}
}
