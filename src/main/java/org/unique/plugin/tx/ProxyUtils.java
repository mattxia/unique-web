package org.unique.plugin.tx;

import java.lang.reflect.Method;

import org.unique.plugin.tx.annotation.Transaction;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**

 * 使用Cglib的代理

 */

public class ProxyUtils implements MethodInterceptor {

	private Object src;

	private ProxyUtils(Object src) {

		this.src = src;

	}

	public static <T> T newProxy(T t) {

		Enhancer en = new Enhancer();

		en.setSuperclass(t.getClass());

		en.setCallback(new ProxyUtils(t));

		Object o = en.create();

		return (T) o;

	}

	public Object intercept(Object proxy, Method method, Object[] args,

	MethodProxy methodProxy) throws Throwable {

		Object o = null;

		if (method.isAnnotationPresent(Transaction.class)) {

			try {

				System.err.println("开始一个事务。。。");

				o = method.invoke(src, args);

				System.err.println("提交一个事务");

			} catch (Exception e) {

				System.err.println("回滚一个事务");

			} finally {

				System.err.println("放回连接池");

			}

		} else {

			o = method.invoke(src, args);

		}

		return o;

	}

}