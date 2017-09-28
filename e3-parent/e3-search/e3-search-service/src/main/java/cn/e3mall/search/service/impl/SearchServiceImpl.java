package cn.e3mall.search.service.impl;

import java.util.List;

import javax.management.Query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchService;

/**
 * 搜索业务层
 * @author fong
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public E3Result ImportSolrIndexLibrary() {
		try {
			//查询solr所有搜索项
			List<SearchItem> list = itemMapper.getItemListWhenStatusIsOne();
			//创建文档对象
			for (SearchItem searchItem : list) {
				SolrInputDocument document=new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title",searchItem.getTitle());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_category_name",searchItem.getCategory_name());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_sell_point",searchItem.getSell_point());
				//写入索引库
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
			//返回成功
			return new E3Result(null);
		} catch (Exception e) {
			e.printStackTrace();
			return new E3Result().build(500, "商品导入失败");
		}
			
	}
	
	
	public SearchResult search(String keyword,int page,int rows) throws Exception{
		//封装搜索条件
		SolrQuery solrQuery=new SolrQuery();
		//设置搜索条件
		solrQuery.setQuery(keyword);
		//设置默认域
		solrQuery.set("df", "item_title");
		//设置分页
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		//高亮
		solrQuery.setHighlight(true);
		//高亮前后缀
		solrQuery.setHighlightSimplePre("<span style='color:red;'>");
		solrQuery.setHighlightSimplePost("</span>");
		//调用Dao
		SearchResult searchResult = searchDao.search(solrQuery);
		//获得总记录数,计算出总页数
		long recourdCount = searchResult.getRecourdCount();
		int totalPages = (int)Math.ceil(recourdCount*1.0/rows);
		searchResult.setTotalPages(totalPages);
		
		return searchResult;
	}


}
