package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.search.service.SearchService;

/**
 * 商城Solr索引库控制层
 * @author fong
 *
 */
@Controller
public class SolrController {
	
	@Autowired
	private SearchService searchItem;

	/**
	 * 商城后台索引库维护
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="index/item/import",method=RequestMethod.POST)
	public E3Result importSolrIndexLibrary(){
		//导入Solr索引库
		E3Result e3Result = searchItem.ImportSolrIndexLibrary();
		return e3Result;
	}
	
	
}
