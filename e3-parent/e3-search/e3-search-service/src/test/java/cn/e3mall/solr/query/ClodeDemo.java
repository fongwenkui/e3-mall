package cn.e3mall.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class ClodeDemo {

	/**
	 * 简单查询(集群版)
	 * @throws Exception
	 */
	@Test
	public void solrQuery() throws Exception {
		//创建ClodeServer集群(参数是zookeeper ip地址+port)
		
		CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.136:2181,192.168.25.136:2182,192.168.25.136:2183");
		//添加默认
		cloudSolrServer.setDefaultCollection("collection2");
		SolrServer solrServer=cloudSolrServer;
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
	
}
