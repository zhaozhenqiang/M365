package zhongxin.m365.bean.older;

public class NewsInfo {

	private String id;// id
	private String title;// 标题
	private String content;// 内容
	private String read_status;
	private String insert_time;

	public NewsInfo() {
	}

	public NewsInfo(String id, String title, String content, String r, String i) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.read_status = r;
		this.insert_time = i;
	}

	@Override
	public String toString() {
		return "NewsInfo [id=" + id + ", title=" + title + ", content="
				+ content + ", read_status=" + read_status + ", insert_time="
				+ insert_time + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRead_status() {
		return read_status;
	}

	public void setRead_status(String read_status) {
		this.read_status = read_status;
	}

	public String getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}

}
