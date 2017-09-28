package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

@Controller
public class SearchCotroller {

	@Autowired
	private SearchService searchService;
	
	//每页显示的条数
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;
	
	
	@RequestMapping(value="search.html")
	public String search(String keyword,@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		keyword=new String(keyword.getBytes("iso8859-1"), "UTF-8");
		//查询商品列表
		SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
		//把结果响应给页面
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages",searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount",searchResult.getRecourdCount());
		model.addAttribute("itemList",searchResult.getItemList());
		return "search";
	}
}
