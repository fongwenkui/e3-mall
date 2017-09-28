package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.common.pojo.SearchItem;

public interface ItemMapper {
	/**
	 * 得到所有SearchItem,查询状态为正常的商品
	 * @return
	 */
	List<SearchItem> getItemListWhenStatusIsOne();
	
	/**
	 * 根据id查询SearchItem,查询状态为正常的商品
	 * @param id
	 * @return
	 */
	SearchItem getItemWhenStatuslsOne(long id);
}
