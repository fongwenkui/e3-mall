package cn.e3mall.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemDescService {
	
	/**
	 * 通过id获取详细描述
	 * @param id
	 * @return E3Result
	 */
	E3Result findItemDescById(long id);
	
	/**
	 * 添加商品描述
	 * @param itemDesc
	 */
	void insertItemDesc(TbItemDesc itemDesc);
}
