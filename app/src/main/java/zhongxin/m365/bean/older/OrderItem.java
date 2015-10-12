package zhongxin.m365.bean.older;

import java.util.ArrayList;

public class OrderItem {
	private String id;// id
	private String cust_id;// id
	private String od_num;
	private String m_id;// 标题
	private String m_mobile;
	private String nd_time;// 内容
	private String car_id;
	private String od_money;
	private String pay_type;
	private String od_line;
	private String pay_status;
	private String insert_time;
	private String plate_num;//
	private String license_p;//添加dispose 1
	private String dispose;
	private String license;//添加license_e 1
	private String brand_id;
	private String type_id;
	private String property;//"1,2"
	private String color_id;
	private String name;
	private String type;
	private String subtype;
	private String area_id;
	private String address;
	private String nickname;
	private String card_no;
	private String sub_name;
	private String mo_str;
	private ArrayList<Mo> mo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getOd_num() {
		return od_num;
	}
	public void setOd_num(String od_num) {
		this.od_num = od_num;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_mobile() {
		return m_mobile;
	}
	public void setM_mobile(String m_mobile) {
		this.m_mobile = m_mobile;
	}
	public String getNd_time() {
		return nd_time;
	}
	public void setNd_time(String nd_time) {
		this.nd_time = nd_time;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getOd_money() {
		return od_money;
	}
	public void setOd_money(String od_money) {
		this.od_money = od_money;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getOd_line() {
		return od_line;
	}
	public void setOd_line(String od_line) {
		this.od_line = od_line;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}
	public String getPlate_num() {
		return plate_num;
	}
	public void setPlate_num(String plate_num) {
		this.plate_num = plate_num;
	}
	public String getLicense_p() {
		return license_p;
	}
	public void setLicense_p(String license_p) {
		this.license_p = license_p;
	}
	public String getDispose() {
		return dispose;
	}
	public void setDispose(String dispose) {
		this.dispose = dispose;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getColor_id() {
		return color_id;
	}
	public void setColor_id(String color_id) {
		this.color_id = color_id;
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
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public String getMo_str() {
		return mo_str;
	}
	public void setMo_str(String mo_str) {
		this.mo_str = mo_str;
	}
	public ArrayList<Mo> getMo() {
		return mo;
	}
	public void setMo(ArrayList<Mo> mo) {
		this.mo = mo;
	}
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", cust_id=" + cust_id + ", od_num="
				+ od_num + ", m_id=" + m_id + ", m_mobile=" + m_mobile
				+ ", nd_time=" + nd_time + ", car_id=" + car_id + ", od_money="
				+ od_money + ", pay_type=" + pay_type + ", od_line=" + od_line
				+ ", pay_status=" + pay_status + ", insert_time=" + insert_time
				+ ", plate_num=" + plate_num + ", license_p=" + license_p
				+ ", dispose=" + dispose + ", license=" + license
				+ ", brand_id=" + brand_id + ", type_id=" + type_id
				+ ", property=" + property + ", color_id=" + color_id
				+ ", name=" + name + ", type=" + type + ", subtype=" + subtype
				+ ", area_id=" + area_id + ", address=" + address
				+ ", nickname=" + nickname + ", card_no=" + card_no
				+ ", sub_name=" + sub_name + ", mo_str=" + mo_str + ", mo="
				+ mo + "]";
	}
	public OrderItem(String id, String cust_id, String od_num, String m_id,
			String m_mobile, String nd_time, String car_id, String od_money,
			String pay_type, String od_line, String pay_status,
			String insert_time, String plate_num, String license_p,
			String dispose, String license, String brand_id, String type_id,
			String property, String color_id, String name, String type,
			String subtype, String area_id, String address, String nickname,
			String card_no, String sub_name, String mo_str, ArrayList<Mo> mo) {
		super();
		this.id = id;
		this.cust_id = cust_id;
		this.od_num = od_num;
		this.m_id = m_id;
		this.m_mobile = m_mobile;
		this.nd_time = nd_time;
		this.car_id = car_id;
		this.od_money = od_money;
		this.pay_type = pay_type;
		this.od_line = od_line;
		this.pay_status = pay_status;
		this.insert_time = insert_time;
		this.plate_num = plate_num;
		this.license_p = license_p;
		this.dispose = dispose;
		this.license = license;
		this.brand_id = brand_id;
		this.type_id = type_id;
		this.property = property;
		this.color_id = color_id;
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.area_id = area_id;
		this.address = address;
		this.nickname = nickname;
		this.card_no = card_no;
		this.sub_name = sub_name;
		this.mo_str = mo_str;
		this.mo = mo;
	}
	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}