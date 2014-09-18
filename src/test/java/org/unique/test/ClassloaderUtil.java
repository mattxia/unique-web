package org.unique.test;

import java.util.Stack;

public class ClassloaderUtil {
	
	public static String getCurrentClassloaderDetail() {

		StringBuffer classLoaderDetail = new StringBuffer();

		Stack<ClassLoader> classLoaderStack = new Stack<ClassLoader>();

		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

		classLoaderDetail.append("\n-----------------------------------------------------------------\n");

		// Build a Stack of the current ClassLoader chain  

		while (currentClassLoader != null) {

			classLoaderStack.push(currentClassLoader);

			currentClassLoader = currentClassLoader.getParent();

		}

		// Print ClassLoader parent chain  

		while (classLoaderStack.size() > 0) {

			ClassLoader classLoader = classLoaderStack.pop();

			// Print current  

			classLoaderDetail.append(classLoader);

			if (classLoaderStack.size() > 0) {

				classLoaderDetail.append("\n--- delegation ---\n");

			} else {

				classLoaderDetail.append(" **Current ClassLoader**");

			}

		}

		classLoaderDetail.append("\n-----------------------------------------------------------------\n");

		return classLoaderDetail.toString();

	}
	
	
	public static void main(String[] args) {
		try {
			Class<?> logger = Class.forName("org.apache.log4j.Logger");
			System.out.println(logger);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}