package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

public class Item extends TbItem {

	public Item(TbItem tbItem){
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}
	
	private String[] images;
	
	public void setImages() {
		String image2 = this.getImage();
		if (image2!=null&&!image2.equals("")) {
			String[] strings = image2.split(",");
			this.images=strings;
		}
	}

	public String[] getImages(){
		String image2 = this.getImage();
		if (image2!=null&&!image2.equals("")) {
			String[] strings = image2.split(",");
			
			return strings;
		}
		return null;
	}
	
}
