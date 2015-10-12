package zhongxin.m365.bean.older;

import java.io.Serializable;


public class DetailsServe implements Serializable{
/*    "id": "96",/
    "b_price": "0",/
    "s_price": "444",/
    "num": "1",
    "car_type": "1",
    "discount": "0",
    "price": "0",
    "is_carsize": "1",
    "serv_name": "444"*/
	private static final long serialVersionUID = 1L;
	private String id;
	private String serv_name;
	private String car_type;
	private String is_carsize;//是否区分大小车 1是区分 2是不区分
	private String b_price;//大车价格
	private String s_price;//小车价格
	private String price;//通用价格 不区分时
    private String num;
    private String discount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServ_name() {
		return serv_name;
	}
	public void setServ_name(String serv_name) {
		this.serv_name = serv_name;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getIs_carsize() {
		return is_carsize;
	}
	public void setIs_carsize(String is_carsize) {
		this.is_carsize = is_carsize;
	}
	public String getB_price() {
		return b_price;
	}
	public void setB_price(String b_price) {
		this.b_price = b_price;
	}
	public String getS_price() {
		return s_price;
	}
	public void setS_price(String s_price) {
		this.s_price = s_price;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "DetailsServe [id=" + id + ", serv_name=" + serv_name
				+ ", car_type=" + car_type + ", is_carsize=" + is_carsize
				+ ", b_price=" + b_price + ", s_price=" + s_price + ", price="
				+ price + ", num=" + num + ", discount=" + discount + "]";
	}
	public DetailsServe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DetailsServe(String id, String serv_name, String car_type,
			String is_carsize, String b_price, String s_price, String price,
			String num, String discount) {
		super();
		this.id = id;
		this.serv_name = serv_name;
		this.car_type = car_type;
		this.is_carsize = is_carsize;
		this.b_price = b_price;
		this.s_price = s_price;
		this.price = price;
		this.num = num;
		this.discount = discount;
	}
    
    
	
}
