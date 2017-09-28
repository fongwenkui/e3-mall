package cn.e3mall.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchService;

public class ItemChangeListener implements MessageListener {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage = null;
			Long itemId = null;
			Thread.sleep(1000);
			// 判断是删除还是新增或修改;
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				String text = textMessage.getText();
				// insert:id号
				String[] split = text.split(":");
				if (split[0].equals("insert") || split[0].equals("update")) {
					// 向索引库添加文档|更新文档
					// 1、根据商品id查询商品信息。
					SearchItem searchItem = itemMapper.getItemWhenStatuslsOne(Long.parseLong(split[1]));
					// 创建一个SolrInputDocument
					SolrInputDocument document = new SolrInputDocument();
					// 使用solrServer对象写入索引库
					document.addField("id", searchItem.getId());
					document.addField("item_title", searchItem.getTitle());
					document.addField("item_price", searchItem.getPrice());
					document.addField("item_category_name", searchItem.getCategory_name());
					document.addField("item_image", searchItem.getImage());
					document.addField("item_sell_point", searchItem.getSell_point());
					// 添加文档
					solrServer.add(document);
					solrServer.commit();
				} else if (split[0].equals("delete")) {
					// 删除索引库文档
					solrServer.deleteById(split[1]);
					solrServer.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
