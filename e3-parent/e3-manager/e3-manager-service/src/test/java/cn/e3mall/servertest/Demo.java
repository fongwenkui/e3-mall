package cn.e3mall.servertest;

import java.util.Scanner;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {

	@Test
	public void testName() throws Exception {
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		Scanner scanner=new Scanner(System.in);
		System.out.println("running");
		scanner.next();
		System.out.println("stop");
	}
	
}
