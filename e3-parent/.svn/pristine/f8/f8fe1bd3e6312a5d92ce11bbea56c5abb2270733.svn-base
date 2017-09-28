package cn.e3mall.item.listener;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 监听商品添加消息，生成静态网页
 * 
 * @author fong
 *
 */
public class HtmlGenListener implements MessageListener {

	@Autowired
	private ItemService ItemService;
	@Resource
	private ItemDescService itemDescService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;

	@Override
	public void onMessage(Message message) {
		try {
			// 创建一个模板，参考jsp
			// 从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			if (StringUtils.isNotBlank(text)) {
				// 分割字符串>insert:id号
				String[] strings = text.split(":");
				// 等待事务提交
				Thread.sleep(1000);
				if (strings[0].equals("delete")) {
					return ;
				}
				// 根据商品id查询商品信息，商品基本信息和商品描述。
				TbItem tbItem = ItemService.findItemById(Long.parseLong(strings[1]));
				// 将tbItem转换成自己的item
				Item item=new Item(tbItem);
//				item.setImages();
				// 取商品描述
				TbItemDesc desc = (TbItemDesc) itemDescService.findItemDescById(item.getId()).getData();
				// 创建一个数据集，把商品数据封装
				Map data = new HashMap<>();
				data.put("itemDesc", desc);
				data.put("item", item);
				// 加载模板对象
				Configuration configuration = freeMarkerConfigurer.getConfiguration();
				Template template = configuration.getTemplate("item.ftl");
				// 创建一个输出流，指定输出的目录及文件名。
				Writer out = new FileWriter(HTML_GEN_PATH + item.getId() + ".html");
				// 生成静态页面。
				template.process(data, out);
				// 关闭流
				out.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
