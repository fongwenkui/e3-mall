package cn.e3mall.solr.query;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class Demo {

	/**
	 * 简单查询
	 * @throws Exception
	 */
	@Test
	public void solrQuery() throws Exception {
		//创建solrServer
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.136:8080/solr");
		//创建查询条件
		SolrQuery solrQuery=new SolrQuery();
		//设置查询条件
		solrQuery.setQuery("*:*");
		//执行查询
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//获得查询结果
		SolrDocumentList documentList = queryResponse.getResults();
		//获得查询总记录数
		long numFound = documentList.getNumFound();
		System.out.println(numFound);
		//遍历文档list
		for (SolrDocument solrDocument : documentList) {
			String title = (String) solrDocument.get("item_title");
			System.out.println(title);
		}
	}
	
	/**
	 * 复杂查询+高量
	 * @throws Exception
	 */
	@Test
	public void solrQueryFuZha() throws Exception {
		//创建solrServer
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.136:8080/solr");
		//创建查询条件
		SolrQuery solrQuery=new SolrQuery();
		//设置查询条件
		solrQuery.setQuery("手机");
		//设置排序
		solrQuery.set("sort", "item_price desc");
		//设置分页
		solrQuery.setStart(0);
		solrQuery.setRows(20);
		//设置默认域
		solrQuery.set("df", "item_title");
		
		//设置高亮
		solrQuery.setHighlight(true);
		//设置高亮前后缀
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		//设置高量的域
		solrQuery.addHighlightField("item_title");
		
		//执行查询
		QueryResponse query = solrServer.query(solrQuery);
		//获得查询结果
		SolrDocumentList solrDocumentList = query.getResults();
		//得到高量域
		Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
		
		//遍历输出
		for (SolrDocument solrDocument : solrDocumentList) {
			//获得标题
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if (list!=null&&list.size()>0) {
				//如果不等于空,并且数量大于0
				title=list.get(0);
			}else{
				title=(String) solrDocument.get("item_title");
			}
			System.out.println(title);
		}
	}
	
	/**
	 * 删除索引和文档
	 * @throws Exception
	 */
	@Test
	public void deleteSolr() throws Exception {
		//创建solrServer
				SolrServer solrServer=new HttpSolrServer("http://192.168.25.136:8080/solr");
				solrServer.deleteById("1100");
//				solrServer.deleteByQuery("item_id:123456");
				solrServer.commit();
	}
	
}
