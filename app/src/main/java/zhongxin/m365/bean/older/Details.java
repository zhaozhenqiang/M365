package zhongxin.m365.bean.older;

import java.util.ArrayList;
/**
"nickname": "忆小臣",
        "pay_state": "2",
        "create_time": "2015-08-13 14:17",
        "m_mobile": "18930614121",
        "pay_type": "1",
        "aptt_money": "200",
        "pay_money": "0",
        "plate_num": "沪A420av",
        "od_money": "6160.00",
        "od_state": "1",
        "car_brand": "奥迪",
        "car_type": "A6",
        "pay_state_cn": "未支付",
        "pay_type_cn": "一卡通",
        "od_state_cn": "已下单",
 */
public class Details {
	private String nickname;
	private String pay_state;
	private String create_time;
	private String m_mobile;
	private String pay_type;
	private String aptt_money;
	private String pay_money;
	private String plate_num;//
	private String od_money;
	private String od_state;
	private String car_brand;// id
	private String car_type;// id
	private String pay_state_cn;// 标题
	private String pay_type_cn;// 内容
	private String od_state_cn;
	private ArrayList<DetailsServe> serv;
	private ArrayList<PackageChildItem> combo;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPay_state() {
		return pay_state;
	}
	public void setPay_state(String pay_state) {
		this.pay_state = pay_state;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getM_mobile() {
		return m_mobile;
	}
	public void setM_mobile(String m_mobile) {
		this.m_mobile = m_mobile;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getAptt_money() {
		return aptt_money;
	}
	public void setAptt_money(String aptt_money) {
		this.aptt_money = aptt_money;
	}
	public String getPay_money() {
		return pay_money;
	}
	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}
	public String getPlate_num() {
		return plate_num;
	}
	public void setPlate_num(String plate_num) {
		this.plate_num = plate_num;
	}
	public String getOd_money() {
		return od_money;
	}
	public void setOd_money(String od_money) {
		this.od_money = od_money;
	}
	public String getOd_state() {
		return od_state;
	}
	public void setOd_state(String od_state) {
		this.od_state = od_state;
	}
	public String getCar_brand() {
		return car_brand;
	}
	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getPay_state_cn() {
		return pay_state_cn;
	}
	public void setPay_state_cn(String pay_state_cn) {
		this.pay_state_cn = pay_state_cn;
	}
	public String getPay_type_cn() {
		return pay_type_cn;
	}
	public void setPay_type_cn(String pay_type_cn) {
		this.pay_type_cn = pay_type_cn;
	}
	public String getOd_state_cn() {
		return od_state_cn;
	}
	public void setOd_state_cn(String od_state_cn) {
		this.od_state_cn = od_state_cn;
	}
	public ArrayList<DetailsServe> getServ() {
		return serv;
	}
	public void setServ(ArrayList<DetailsServe> serv) {
		this.serv = serv;
	}
	public ArrayList<PackageChildItem> getCombo() {
		return combo;
	}
	public void setCombo(ArrayList<PackageChildItem> combo) {
		this.combo = combo;
	}
	@Override
	public String toString() {
		return "Details [nickname=" + nickname + ", pay_state=" + pay_state
				+ ", create_time=" + create_time + ", m_mobile=" + m_mobile
				+ ", pay_type=" + pay_type + ", aptt_money=" + aptt_money
				+ ", pay_money=" + pay_money + ", plate_num=" + plate_num
				+ ", od_money=" + od_money + ", od_state=" + od_state
				+ ", car_brand=" + car_brand + ", car_type=" + car_type
				+ ", pay_state_cn=" + pay_state_cn + ", pay_type_cn="
				+ pay_type_cn + ", od_state_cn=" + od_state_cn + ", serv="
				+ serv + ", combo=" + combo + "]";
	}
	public Details(String nickname, String pay_state, String create_time,
			String m_mobile, String pay_type, String aptt_money,
			String pay_money, String plate_num, String od_money,
			String od_state, String car_brand, String car_type,
			String pay_state_cn, String pay_type_cn, String od_state_cn,
			ArrayList<DetailsServe> serv, ArrayList<PackageChildItem> combo) {
		super();
		this.nickname = nickname;
		this.pay_state = pay_state;
		this.create_time = create_time;
		this.m_mobile = m_mobile;
		this.pay_type = pay_type;
		this.aptt_money = aptt_money;
		this.pay_money = pay_money;
		this.plate_num = plate_num;
		this.od_money = od_money;
		this.od_state = od_state;
		this.car_brand = car_brand;
		this.car_type = car_type;
		this.pay_state_cn = pay_state_cn;
		this.pay_type_cn = pay_type_cn;
		this.od_state_cn = od_state_cn;
		this.serv = serv;
		this.combo = combo;
	}
	public Details() {
		super();
		// TODO Auto-generated constructor stub
	}




}
