package cn.e3mall.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.pojo.TbItem;

public interface ItemService {

	/**
	 * 根据id查item
	 */
	TbItem findItemById(long id);
	
	/**
	 * 查询商品(分页)
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUiDataGridResult findItemListPage(Integer page,Integer rows);
	
	/**
	 * 添加商品
	 * @param tbItem
	 * @return
	 */
	E3Result insertItem(TbItem tbItem,String desc);
	
	/**
	 * 修改商品
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	E3Result updateItem(TbItem tbItem,String desc);

	/**
	 * 删除商品
	 * @param ids
	 * @return
	 */
	E3Result deleteItemById(Long ids);

	/**
	 * 商品下架
	 * @param ids
	 * @return
	 */
	E3Result instock(Long ids);

	/**
	 * 商品上架
	 * @param ids
	 * @return
	 */
	E3Result reshelf(Long ids);
	
}
