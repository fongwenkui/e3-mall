package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping("item/{item_id}.html")
	public String showItemInfo(@PathVariable String item_id,Model model){
		//根据ID查找对应的商品
		TbItem tbItem = itemService.findItemById(Long.parseLong(item_id));
		//取商品描述信息
		 Object itemDesc= itemDescService.findItemDescById(Long.parseLong(item_id)).getData();
		//转换成自己的tbItem
		Item item=new Item(tbItem);
		model.addAttribute("item",item);
		//判断商品描述信息是否为空
		if (itemDesc!=null) {
			model.addAttribute("itemDesc",itemDesc);
		}
		return "item";
	}
	
}
