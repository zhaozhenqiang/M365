package zhongxin.m365.bean.older;

public 	class Mo{
	String id;
	String o_id;
	String s_id;
	String name;
	String type;
	String is_carsize;
	String price;
	String bvehicle_price;
	String svehicle_price;
	
	@Override
	public String toString() {
		return "Mo [id=" + id + ", o_id=" + o_id + ", s_id=" + s_id + ", name="
				+ name + ", type=" + type + ", is_carsize=" + is_carsize
				+ ", price=" + price + ", bvehicle_price=" + bvehicle_price
				+ ", svehicle_price=" + svehicle_price + "]";
	}

	public Mo(String id, String o_id, String s_id, String name, String type,
			String is_carsize, String price, String bvehicle_price,
			String svehicle_price) {
		super();
		this.id = id;
		this.o_id = o_id;
		this.s_id = s_id;
		this.name = name;
		this.type = type;
		this.is_carsize = is_carsize;
		this.price = price;
		this.bvehicle_price = bvehicle_price;
		this.svehicle_price = svehicle_price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getO_id() {
		return o_id;
	}

	public void setO_id(String o_id) {
		this.o_id = o_id;
	}

	public String getS_id() {
		return s_id;
	}

	public void setS_id(String s_id) {
		this.s_id = s_id;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public Mo() {
		super();
	}
	
	
}
