package zhongxin.m365.bean.older;

public class OrderCar {

	private String car_id;// id
	private String plate_num;// 标题
	private String brand_id;// 内容
	private String type_id;
	private String carbrand;
	private String cartype;
	private String selected;
	private String alternate2;

	public OrderCar() {
	}

	public OrderCar(String car_id, String plate_num, String brand_id,
			String type_id, String carbrand, String cartype, String selected,
			String alternate2) {
		super();
		this.car_id = car_id;
		this.plate_num = plate_num;
		this.brand_id = brand_id;
		this.type_id = type_id;
		this.carbrand = carbrand;
		this.cartype = cartype;
		this.selected = selected;
		this.alternate2 = alternate2;
	}

	@Override
	public String toString() {
		return "OrderCar [car_id=" + car_id + ", plate_num=" + plate_num
				+ ", brand_id=" + brand_id + ", type_id=" + type_id
				+ ", carbrand=" + carbrand + ", cartype=" + cartype
				+ ", selected=" + selected + ", alternate2=" + alternate2
				+ "]";
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getPlate_num() {
		return plate_num;
	}

	public void setPlate_num(String plate_num) {
		this.plate_num = plate_num;
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

	public String getCarbrand() {
		return carbrand;
	}

	public void setCarbrand(String carbrand) {
		this.carbrand = carbrand;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getAlternate2() {
		return alternate2;
	}

	public void setAlternate2(String alternate2) {
		this.alternate2 = alternate2;
	}



}
