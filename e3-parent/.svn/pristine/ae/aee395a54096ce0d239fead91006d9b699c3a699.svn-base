package cn.e3mall.redis;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class Demo {

	/**
	 * Jedis单机
	 * @throws Exception
	 */
	@Test
	public void jedisTest() throws Exception {
		//创建一个连接Jedis对象，参数：host、port
		Jedis jedis=new Jedis("192.168.25.132",6379);
		//直接使用jedis操作redis。所有jedis的命令都对应一个方法。
		jedis.set("key1", "hello jedis");
		String string=jedis.get("key1");
		System.out.println(string);
		//关闭连接
		jedis.close();
	}
	/**
	 * Jedis连接池(单机)
	 * @throws Exception
	 */
	@Test
	public void jedisPoolTest() throws Exception{
		//创建一个连接池对象，两个参数host、port
		JedisPool jedisPool=new JedisPool("192.168.25.132",6379);
		//从连接池获得一个连接，就是一个jedis对象。
		Jedis jedis = jedisPool.getResource();
		//使用jedis操作redis
		jedis.set("key1", "hello jedis");
		String string=jedis.get("key1");
		System.out.println(string);
		//关闭jedis,好让池子回收资源
		jedis.close();
		//关闭池子
		jedisPool.close();
	}
	
	/**
	 * JedisCluster:Jedis集群
	 * @throws Exception
	 */
	@Test
	public void jedisd() throws Exception {
		//创建一个JedisCluster对象。有一个参数nodes是一个set类型。set中包含若干个HostAndPort对象。
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.132", 7001));
		nodes.add(new HostAndPort("192.168.25.132", 7002));
		nodes.add(new HostAndPort("192.168.25.132", 7003));
		nodes.add(new HostAndPort("192.168.25.132", 7004));
		nodes.add(new HostAndPort("192.168.25.132", 7005));
		nodes.add(new HostAndPort("192.168.25.132", 7006));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		//直接使用JedisCluster对象操作redis。
		jedisCluster.set("key1", "hello JedisCluster");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		
	}
	
}
