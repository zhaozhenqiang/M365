package zhongxin.m365.bean.older;

public class PackageChildItem {

	/**
	 * "service_id": "2", "total_times": "1", "service_times": "1", "use_times":
	 * "0", "serv_name": "养护", "ass_id": "122", "ass_baltimes": "1"
	 */
	private String service_id;// id
	private String total_times;// 总次数
	private String service_times;//套餐服务次数
	private String use_times;//套餐使用次数
	private String serv_name;
	private String ass_id;//哪个套餐中的服务表示
	private String ass_baltimes;//剩余次数
	private String serv_price;
	private int number;

	public PackageChildItem() {
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getTotal_times() {
		return total_times;
	}

	public void setTotal_times(String total_times) {
		this.total_times = total_times;
	}

	public String getService_times() {
		return service_times;
	}

	public void setService_times(String service_times) {
		this.service_times = service_times;
	}

	public String getUse_times() {
		return use_times;
	}

	public void setUse_times(String use_times) {
		this.use_times = use_times;
	}

	public String getServ_name() {
		return serv_name;
	}

	public void setServ_name(String serv_name) {
		this.serv_name = serv_name;
	}

	public String getAss_id() {
		return ass_id;
	}

	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}

	public String getAss_baltimes() {
		return ass_baltimes;
	}

	public void setAss_baltimes(String ass_baltimes) {
		this.ass_baltimes = ass_baltimes;
	}

	public String getServ_price() {
		return serv_price;
	}

	public void setServ_price(String serv_price) {
		this.serv_price = serv_price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PackageChildItem [service_id=" + service_id + ", total_times="
				+ total_times + ", service_times=" + service_times
				+ ", use_times=" + use_times + ", serv_name=" + serv_name
				+ ", ass_id=" + ass_id + ", ass_baltimes=" + ass_baltimes
				+ ", serv_price=" + serv_price + ", number=" + number
				+ "]";
	}

	public PackageChildItem(String service_id, String total_times,
			String service_times, String use_times, String serv_name,
			String ass_id, String ass_baltimes, String serv_price,
			int number) {
		super();
		this.service_id = service_id;
		this.total_times = total_times;
		this.service_times = service_times;
		this.use_times = use_times;
		this.serv_name = serv_name;
		this.ass_id = ass_id;
		this.ass_baltimes = ass_baltimes;
		this.serv_price = serv_price;
		this.number = number;
	}

}
