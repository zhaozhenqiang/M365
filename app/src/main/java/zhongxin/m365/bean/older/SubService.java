package zhongxin.m365.bean.older;

import java.io.Serializable;


public class SubService implements Serializable{
	// "id": "99",
	// "cid": "11",
	// "title": "test",
	// "is_dis": "1",
	// "status": "1",
	// "is_front": "1",
	// "price": "0.00",
	// "price_b": "10.00",
	// "price_s": "5.00"

	/**
	 * 已弃用，备删除
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String cid;
	private String title;
	private String price;
	private String is_front;
	private String price_b;
	private String price_s;
	private String is_dis;
	private String status;
    private String flag;//s是否被选中  1选中，2 未选中
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIs_front() {
		return is_front;
	}

	public void setIs_front(String is_front) {
		this.is_front = is_front;
	}

	public String getPrice_b() {
		return price_b;
	}

	public void setPrice_b(String price_b) {
		this.price_b = price_b;
	}

	public String getPrice_s() {
		return price_s;
	}

	public void setPrice_s(String price_s) {
		this.price_s = price_s;
	}

	public String getIs_dis() {
		return is_dis;
	}

	public void setIs_dis(String is_dis) {
		this.is_dis = is_dis;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
