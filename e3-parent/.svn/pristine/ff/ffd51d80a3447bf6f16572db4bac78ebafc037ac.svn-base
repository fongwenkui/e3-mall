package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;

@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery solrQuery) throws Exception{
		//执行查询
		QueryResponse query = solrServer.query(solrQuery);
		//获得查询结果
		SolrDocumentList solrDocumentList = query.getResults();
		//获得高亮域
		Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
		//遍历获得List<SearchItem并封装成SearchResult
		List<SearchItem> list=new ArrayList<>();
		SearchResult searchResult=new SearchResult();
		//封装recourdCount
		searchResult.setRecourdCount(solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem searchItem=new SearchItem();
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			//标题为高亮
			List<String> title_list = highlighting.get(solrDocument.get("id")).get("item_title");
			//判断
			if (title_list!=null&&title_list.size()>0) {
				searchItem.setTitle(title_list.get(0));
			}else searchItem.setTitle((String) solrDocument.get("item_title"));
			list.add(searchItem);
		}
		searchResult.setItemList(list);
		return searchResult;
	}
	
}
