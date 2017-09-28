package cn.e3mall.mybatis;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;

public class Demo {
	
	private ItemMapper itemMapper;

	@Test
	public void myBatisTest() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
		itemMapper=(ItemMapper) applicationContext.getBean("itemMapper");
		List<SearchItem> list = itemMapper.getItemListWhenStatusIsOne();
		System.out.println(list);
	}
}
