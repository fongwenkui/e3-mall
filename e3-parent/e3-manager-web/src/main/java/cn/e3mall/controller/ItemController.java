package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	/**
	 * 测试Demo
	 * @param id
	 * @return
	 */
	@RequestMapping("item/{id}")
	@ResponseBody
	public TbItem findItemById(@PathVariable Long id){
		return itemService.findItemById(id);
	}
	
	/**
	 * 查询商品(分页)
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("item/list")
	public EasyUiDataGridResult ShowItemListPage(Integer page,Integer rows ){
		EasyUiDataGridResult easyUiDataGridResult = itemService.findItemListPage(page, rows);
		return easyUiDataGridResult;
	}
	
	/**
	 * 添加商品
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	public E3Result addProduct(TbItem tbItem,String desc){
		E3Result e3Result = itemService.insertItem(tbItem, desc);
		return e3Result;
	}
	
	/**
	 * 到编辑页面
	 */
	@RequestMapping(value="rest/page/item-edit")
	public String showEditItem(){
		return "item-edit";
	}
	
	/**
	 * 到编辑页面(加载商品信息)
	 */
	@RequestMapping(value="/rest/item/query/item/desc/{id}")
	@ResponseBody
	public E3Result showEditItemDesc(@PathVariable Long id){
		E3Result e3Result = itemDescService.findItemDescById(id);
		return e3Result;
	}
	
	/**
	 * 到编辑页面(加载商品信息)
	 */
	@RequestMapping(value="rest/item/update")
	@ResponseBody
	public E3Result editItem(TbItem tbItem,String desc){
		E3Result e3Result = itemService.updateItem(tbItem, desc);
		return e3Result;
	}
	
	
	/**
	 * 删除商品
	 */
	@RequestMapping(value="/rest/item/delete")
	@ResponseBody
	public E3Result deleteItem(Long ids){
		E3Result e3Result = itemService.deleteItemById(ids);
		return e3Result;
	}
	
	/**
	 * 商品下架
	 */
	@RequestMapping(value="rest/item/instock")
	@ResponseBody
	public E3Result instock(Long ids){
		E3Result e3Result = itemService.instock(ids);
		return e3Result;
	}
	
	/**
	 * 商品上架
	 */
	@RequestMapping(value="/rest/item/reshelf")
	@ResponseBody
	public E3Result reshelf(Long ids){
		E3Result e3Result = itemService.reshelf(ids);
		return e3Result;
	}
	
	
}
