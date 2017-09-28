package cn.e3mall.tortal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

@Controller
public class PageController {

	@Autowired
	private ContentService contentService;
	
	@Value("${CONTENT_LUNBO_ID}")
	private Integer CONTENT_LUNBO_ID;
	
	@RequestMapping(value="/index.html")
	public String index(Model model){
		//从后台Tb_content表中取出广告位的内容
		List<TbContent> ad1List = contentService.findContentById(CONTENT_LUNBO_ID);
		model.addAttribute("ad1List", ad1List);
		return "index";
	}
	
}
