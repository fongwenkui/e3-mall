package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.sym.Name;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeResult;
import cn.e3mall.content.service.ContentCategoryService;

@Controller
public class ContentCatController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 显示所有分类
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value="/content/category/list")
	@ResponseBody
	public List<EasyUiTreeResult> showAllContentCategory(@RequestParam(defaultValue="0",name="id") Long parentId){
		List<EasyUiTreeResult> list = contentCategoryService.findAllContentCategory(parentId);
		return list;
	}
	
	/**
	 * 创建分类节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result createNode(Long parentId,String name){
		E3Result e3Result = contentCategoryService.insertContentCategory(parentId, name);
		return e3Result;
	}
	
	/**
	 * 重命名分类节点
	 */
	@RequestMapping(value="/content/category/update",method=RequestMethod.POST)
	@ResponseBody
	public E3Result rename(Long id,String name){
		E3Result e3Result=contentCategoryService.updateContentCategory(id, name);
		return e3Result;
	}
	
	@RequestMapping(value="/content/category/delete/",method=RequestMethod.POST)
	@ResponseBody
	public E3Result rename(Long id){
		E3Result e3Result=contentCategoryService.deleteContentCategory(id);
		return e3Result;
	}
}
