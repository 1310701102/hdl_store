package cn.taijiweibu.hdl.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("hdl_good")
public class Good {
	
	@Id
	protected int id;
	@Name
	protected String name;
	@Column("p_id")
	protected String productorId;
	@Column
	protected String price;
	@Column("ds")
	protected String describe;
	@Column
	protected String count;
	@Column("img_loc")
	protected String img_location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductorId() {
		return productorId;
	}
	public void setProductorId(String productorId) {
		this.productorId = productorId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getImg_location() {
		return img_location;
	}
	public void setImg_location(String img_location) {
		this.img_location = img_location;
	}
	
	
	
}
