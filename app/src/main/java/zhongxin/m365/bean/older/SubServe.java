package zhongxin.m365.bean.older;

import java.io.Serializable;


public class SubServe implements Serializable{
/*    "id": "1",
    "name": "日式洗车",
    "type": "2",
    "is_carsize": "",
    "price": "",
    "bvehicle_price": "1.00",
    "svehicle_price": "2.00",
    "title": "美容"*/
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String type;
	private String is_carsize;//是否区分大小车 1是区分 2是不区分
	private String bvehicle_price;//大车价格
	private String svehicle_price;//小车价格
	private String price;//通用价格 不区分时
	private String title;
    private String flag;//s是否被选中  1选中，2 未选中
    
    private int number;
    private int favorable;
    
    
    
    public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getFavorable() {
		return favorable;
	}
	public void setFavorable(int favorable) {
		this.favorable = favorable;
	}
	//number price  优惠
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIs_carsize() {
		return is_carsize;
	}
	public void setIs_carsize(String is_carsize) {
		this.is_carsize = is_carsize;
	}
	public String getBvehicle_price() {
		return bvehicle_price;
	}
	public void setBvehicle_price(String bvehicle_price) {
		this.bvehicle_price = bvehicle_price;
	}
	public String getSvehicle_price() {
		return svehicle_price;
	}
	public void setSvehicle_price(String svehicle_price) {
		this.svehicle_price = svehicle_price;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
