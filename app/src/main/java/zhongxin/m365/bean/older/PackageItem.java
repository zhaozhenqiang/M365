package zhongxin.m365.bean.older;

import java.util.ArrayList;

public class PackageItem {
/**
            "combo_id": "1",
            "name": "test1",
            "order_id": "17",
            "mobile": "18930614121",
            "items": [
	*/
	private String combo_id;// id
	private String name;// 标题
	private String order_id;// 内容
	private String mobile;
	
	private ArrayList<PackageChildItem> items;
	private String extString;
	private String extString2;

	public PackageItem() {
	}

	public String getCombo_id() {
		return combo_id;
	}

	public void setCombo_id(String combo_id) {
		this.combo_id = combo_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public ArrayList<PackageChildItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<PackageChildItem> items) {
		this.items = items;
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

	@Override
	public String toString() {
		return "PackageItem [combo_id=" + combo_id + ", name=" + name
				+ ", order_id=" + order_id + ", mobile=" + mobile + ", items="
				+ items + ", extString=" + extString + ", extString2="
				+ extString2 + "]";
	}

	public PackageItem(String combo_id, String name, String order_id,
			String mobile, ArrayList<PackageChildItem> items, String extString,
			String extString2) {
		super();
		this.combo_id = combo_id;
		this.name = name;
		this.order_id = order_id;
		this.mobile = mobile;
		this.items = items;
		this.extString = extString;
		this.extString2 = extString2;
	}
	
	

}
