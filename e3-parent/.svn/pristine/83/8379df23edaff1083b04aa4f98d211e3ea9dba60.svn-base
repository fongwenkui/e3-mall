package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;

	/**
	 * 显示EasyUiDataGridResult
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/content/query/list",method=RequestMethod.GET)
	@ResponseBody
	public EasyUiDataGridResult list(Long categoryId,Integer page,Integer rows){
		EasyUiDataGridResult easyUiDataGridResult = contentService.getContentPageByCategoryId(categoryId, page, rows);
		return easyUiDataGridResult;
	}
	
	/**
	 * 保存
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result save(TbContent content){
		E3Result e3Result = contentService.saveContent(content);
		return e3Result;
	}
	
	/**
	 * 编辑
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/rest/content/edit",method=RequestMethod.POST)
	@ResponseBody
	public E3Result update(TbContent content){
		E3Result e3Result = contentService.updateContent(content);
		return e3Result;
	}
	
	/**
	 * 删除
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/content/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result delete(Long ids){
		E3Result e3Result=contentService.deleteContentById(ids);
		return e3Result;
	}
	
}
