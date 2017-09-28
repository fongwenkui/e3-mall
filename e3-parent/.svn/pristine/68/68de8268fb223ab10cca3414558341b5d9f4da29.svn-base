package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import cn.e3mall.common.pojo.EasyUiTreeResult;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<EasyUiTreeResult> findItemCatByParentId(long parent_id) {
		TbItemCatExample tbItemCatExample=new TbItemCatExample();
		Criteria criteria = tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parent_id);
		
		List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);
		//手动封装成EasyUiTreeResult对象集合
		List<EasyUiTreeResult> easyUiTreeResults_list=new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUiTreeResult easyUiTreeResult=new EasyUiTreeResult();
			easyUiTreeResult.setId(tbItemCat.getId());
			easyUiTreeResult.setText(tbItemCat.getName());
			easyUiTreeResult.setState(tbItemCat.getIsParent()?"closed":"open");
			easyUiTreeResults_list.add(easyUiTreeResult);
		}
		return easyUiTreeResults_list;
		
	}

}
