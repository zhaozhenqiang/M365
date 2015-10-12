package zhongxin.m365.bean.older;

public class ServeItem {
/**
            "id": "14",
            "order_num": "SCG20150806164606JmHYb5",
            "plate_num": "沪A420av",
            "od_money": "0",
            "nd_time": "1970-01-01 08:00",
            "pay_state": "1",
            "od_state": "1",
            "nickname": "忆小臣",
            "mo_str": "66df",
            "od_state_cn": "已下单",
            "pay_state_cn": "已支付"
    	*/
	private String id;// id
	private String order_num;// 标题
	private String plate_num;// 内容
	private String od_money;
	private String nd_time;
	private String pay_state;
	private String od_state;
	private String nickname;
	private String mo_str;
	private String od_state_cn;
	private String pay_state_cn;
	
	private String extString;
	private String extString2;
	private String extString3;
	
	
	
	public ServeItem() {
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getOrder_num() {
		return order_num;
	}



	public void setOrder_num(String order_num) {
		this.order_num = order_num;
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



	public String getNd_time() {
		return nd_time;
	}



	public void setNd_time(String nd_time) {
		this.nd_time = nd_time;
	}



	public String getPay_state() {
		return pay_state;
	}



	public void setPay_state(String pay_state) {
		this.pay_state = pay_state;
	}



	public String getOd_state() {
		return od_state;
	}



	public void setOd_state(String od_state) {
		this.od_state = od_state;
	}



	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	public String getMo_str() {
		return mo_str;
	}



	public void setMo_str(String mo_str) {
		this.mo_str = mo_str;
	}



	public String getOd_state_cn() {
		return od_state_cn;
	}



	public void setOd_state_cn(String od_state_cn) {
		this.od_state_cn = od_state_cn;
	}



	public String getPay_state_cn() {
		return pay_state_cn;
	}



	public void setPay_state_cn(String pay_state_cn) {
		this.pay_state_cn = pay_state_cn;
	}



	public String getExtString() {
		return extString;
	}



	public void setExtString(String extString) {
		this.extString = extString;
	}



	public String getExtString2() {
		return extString2;
	}



	public void setExtString2(String extString2) {
		this.extString2 = extString2;
	}



	public String getExtString3() {
		return extString3;
	}



	public void setExtString3(String extString3) {
		this.extString3 = extString3;
	}



	@Override
	public String toString() {
		return "ServeItem [id=" + id + ", order_num=" + order_num
				+ ", plate_num=" + plate_num + ", od_money=" + od_money
				+ ", nd_time=" + nd_time + ", pay_state=" + pay_state
				+ ", od_state=" + od_state + ", nickname=" + nickname
				+ ", mo_str=" + mo_str + ", od_state_cn=" + od_state_cn
				+ ", pay_state_cn=" + pay_state_cn + ", extString=" + extString
				+ ", extString2=" + extString2 + ", extString3=" + extString3
				+ "]";
	}



	public ServeItem(String id, String order_num, String plate_num,
			String od_money, String nd_time, String pay_state, String od_state,
			String nickname, String mo_str, String od_state_cn,
			String pay_state_cn, String extString, String extString2,
			String extString3) {
		super();
		this.id = id;
		this.order_num = order_num;
		this.plate_num = plate_num;
		this.od_money = od_money;
		this.nd_time = nd_time;
		this.pay_state = pay_state;
		this.od_state = od_state;
		this.nickname = nickname;
		this.mo_str = mo_str;
		this.od_state_cn = od_state_cn;
		this.pay_state_cn = pay_state_cn;
		this.extString = extString;
		this.extString2 = extString2;
		this.extString3 = extString3;
	}


}
