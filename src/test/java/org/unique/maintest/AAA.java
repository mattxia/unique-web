package org.unique.maintest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class AAA {

	public void invoke(){
		System.out.println("呵呵");
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface AdviceI{
		String value();
	}
	
	interface Advice{
		void interceptor();
	}
	
	@AdviceI("0")
	class Advice1 implements Advice{

		@Override
		public void interceptor() {
			System.out.println("前置1");
		}
		
	}
	@AdviceI("1")
	class Advice2 implements Advice{

		@Override
		public void interceptor() {
			System.out.println("前置2");
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		AAA a = new AAA();
		List<Advice> adviceList = new ArrayList<Advice>();
		Advice a1 = new AAA().new Advice1();
		Advice a2 = new AAA().new Advice2();
		adviceList.add(a2);
		adviceList.add(a1);
		
		List<Advice> adviceList2 = new ArrayList<Advice>();
		adviceList2.addAll(adviceList);
		for(Advice advice : adviceList){
			if(null != advice.getClass().getAnnotation(AdviceI.class)){
				String v = advice.getClass().getAnnotation(AdviceI.class).value();
				adviceList2.set(Integer.valueOf(v), advice);
			}
		}
		for(Advice advice : adviceList2){
			advice.interceptor();
		}
		a.invoke();
	}
}
