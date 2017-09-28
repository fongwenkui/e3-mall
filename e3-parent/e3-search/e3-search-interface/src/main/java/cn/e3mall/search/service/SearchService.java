package cn.e3mall.search.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.SearchResult;

public interface SearchService {

	/**
	 * 导入solr索引库
	 * @return
	 */
	E3Result ImportSolrIndexLibrary();
	
	/**
	 * 搜索(分页)
	 * @param keyword 搜索条件名
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @return
	 */
	SearchResult search(String keyword,int page,int rows) throws Exception;
	
	
}
