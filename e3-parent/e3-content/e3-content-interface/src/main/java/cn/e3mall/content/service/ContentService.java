package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.pojo.TbContent;

public interface ContentService {
	/**
	 * 单击内容管理树菜单显示datagird分页数据
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 */
	EasyUiDataGridResult getContentPageByCategoryId(Long categoryId, Integer page, Integer size);
	
	/**
	 * 保存内容
	 * @param content
	 * @return
	 */
	E3Result saveContent(TbContent content);
	
	/**
	 * 修改内容
	 * @param content
	 * @return
	 */
	E3Result updateContent(TbContent content);
	
	/**
	 * 取出内容，通过category_id
	 * @param id
	 * @return
	 */
	List<TbContent> findContentById(long category_id);

	/**
	 * 删除，通过Id
	 * @param ids
	 * @return
	 */
	E3Result deleteContentById(Long ids);
	
	
}
