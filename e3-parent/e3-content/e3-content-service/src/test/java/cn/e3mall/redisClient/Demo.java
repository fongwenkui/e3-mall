package cn.e3mall.redisClient;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;

public class Demo {

	@Test
	public void JedisClientTest() throws Exception {
		//策略模式(接口+实现类)，优点:只用修改配置文件，不需改源码
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//参数给接口类型
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("key1", "Cluster and Pool");
		System.out.println(jedisClient.get("key1"));
		
		
	}
	
}
