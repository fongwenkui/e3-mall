package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeResult;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapping;

	@Override
	public List<EasyUiTreeResult> findAllContentCategory(long parentId) {
		// 添加条件parentId
		TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
		Criteria criteria = tbContentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 查询
		List<TbContentCategory> list = contentCategoryMapping.selectByExample(tbContentCategoryExample);
		// 遍历封装成EasyUiTreeResult
		List<EasyUiTreeResult> list_tree = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUiTreeResult easyUiTreeResult = new EasyUiTreeResult();
			easyUiTreeResult.setId(tbContentCategory.getId());
			easyUiTreeResult.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			easyUiTreeResult.setText(tbContentCategory.getName());
			list_tree.add(easyUiTreeResult);
		}
		return list_tree;
	}

	@Override
	public E3Result insertContentCategory(Long parentId, String text) {
		try {
			TbContentCategory content = new TbContentCategory();
			content.setParentId(parentId);
			// 新创建的节点肯定是没有子节点，所以is_parent是0
			content.setIsParent(false);
			content.setName(text);
			Date date = new Date();
			content.setCreated(date);
			content.setUpdated(date);
			// 1(正常),2(删除)
			content.setStatus(1);
			// 默认排序就是1
			content.setSortOrder(1);
			// 插入数据库,插入后将id返回给插入的对象中
			contentCategoryMapping.insert(content);
			TbContentCategory parent = contentCategoryMapping.selectByPrimaryKey(parentId);
			if (!parent.getIsParent()) {
				parent.setIsParent(true);
				contentCategoryMapping.updateByPrimaryKey(parent);
			}
			
			// 创建E3Result
			E3Result e3Result = new E3Result(content);
			return e3Result;
		} catch (Exception e) {
			return new E3Result();
		}

	}

	@Override
	public E3Result updateContentCategory(long id, String name) {
		try {
			TbContentCategory content = new TbContentCategory();
			content.setId(id);
			content.setName(name);
			content.setUpdated(new Date());
			contentCategoryMapping.updateByPrimaryKeySelective(content);
			E3Result e3Result = new E3Result(content);
			return e3Result;
		} catch (Exception e) {
			return new E3Result();
		}

	}

	@Override
	public E3Result deleteContentCategory(Long id) {
		try {
			// 递归删除
			// 判断是否是is_parent
			TbContentCategory content = contentCategoryMapping.selectByPrimaryKey(id);
			Boolean isParent = content.getIsParent();
			Long id2 = content.getId();
			if (isParent) {
				// 添加parent_id条件
				TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
				Criteria criteria = tbContentCategoryExample.createCriteria();
				criteria.andParentIdEqualTo(id2);
				// 查询
				List<TbContentCategory> list = contentCategoryMapping.selectByExample(tbContentCategoryExample);
				// 递归删除
				for (TbContentCategory tbContentCategory : list) {
					deleteContentCategory(tbContentCategory.getId());
					
				}
			} 
			//直接删除
			contentCategoryMapping.deleteByPrimaryKey(id);

		} catch (Exception e) {
			return new E3Result();
		}
		E3Result e3Result = new E3Result(null);
		return e3Result;
	}

}
