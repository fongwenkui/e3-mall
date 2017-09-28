package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUiTreeResult;
import cn.e3mall.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService ics;
	
	/**
	 * 添加商品页面中获得菜单
	 * @return
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUiTreeResult> AddProductGetCat(@RequestParam(name="id",defaultValue="0") Long parent_id){
		List<EasyUiTreeResult> list = ics.findItemCatByParentId(parent_id);
		return list;
	}
	
}
