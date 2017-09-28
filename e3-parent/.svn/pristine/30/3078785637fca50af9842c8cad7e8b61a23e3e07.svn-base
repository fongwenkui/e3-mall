package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeResult;

public interface ContentCategoryService {

	/**
	 * 显示所有content分类
	 * @return
	 */
	List<EasyUiTreeResult> findAllContentCategory(long parentId);
	
	/**
	 * 创建节点分类
	 * @param parentId
	 * @param text
	 * @return
	 */
	E3Result insertContentCategory(Long parentId,String text);
	
	/**
	 * 重命名节点分类
	 * @param id
	 * @param name
	 * @return
	 */
	E3Result updateContentCategory(long id,String name);

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	E3Result deleteContentCategory(Long id);
	
}
