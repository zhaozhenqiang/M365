package zhongxin.m365.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import zhongxin.m365.R;
import zhongxin.m365.activity.older.NewsDetailActivity;
import zhongxin.m365.bean.older.NewsInfo;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.ToastUtils;
import zhongxin.m365.views.XListView;
import zhongxin.m365.views.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * erp 消息主页面
 * @author zzq
 */
public class NewsFragment extends Fragment implements IXListViewListener,
		OnItemClickListener {

	@ViewInject(R.id.news_xlistview)
	private XListView news_xlistview;
	@ViewInject(R.id.basetitle)
	private TextView basetitle;
	public static final String TAG = "NewsFragment";
	private String sort = "1";
	private String uid;
	public ArrayList<NewsInfo> news = new ArrayList<NewsInfo>();
	NewsAdapter newsAdapter;
	public int offset = 10;// 每页偏移量
	public int page = 1;// 页数
	public int sumPage;// 总页数
	public int num;// 总条数
	@ViewInject(R.id.total)
	private LinearLayout total;
	@ViewInject(R.id.news_num)
	private TextView news_num;
	@ViewInject(R.id.baseback)
	private Button baseback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUid();
	}

	private void initUid() {
		uid = getActivity().getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE).getString("uid", "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, container, false);
		ViewUtils.inject(this, view);
		basetitle.setText("消息盒子");
		baseback.setVisibility(View.GONE);
		news_xlistview.setPullLoadEnable(true); // 设置可滑动
		news_xlistview.setXListViewListener(this);// 设置滑动监听器
		news_xlistview.setOnItemClickListener(this);
		
		newsAdapter = new NewsAdapter(getActivity(),news);
		news_xlistview.setAdapter(newsAdapter);
		 
		numClient("");
		mHttpClient(1, "2");
		return view;
	}
	@OnClick({ R.id.news_num })

private void mOnClick(View v) {
	switch (v.getId()) {
	case R.id.news_num:
		news_num.setVisibility(View.GONE);
		mHttpClient(page, "noread");
	
		}
	}
	
	

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				newsAdapter = new NewsAdapter(getActivity(), news);
				news_xlistview.setAdapter(newsAdapter);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
				String currentDate = df.format(new Date()); // 开始时间
				news_xlistview.stopRefresh();
				news_xlistview.setRefreshTime(currentDate);
				break;
			case 1:
				newsAdapter.notifyDataSetChanged();
				news_xlistview.stopLoadMore();
				break;
			default:
				break;
			}
		};
	};

	private void mUpdateView() {
		if (page == 1) {
			Message message = new Message();
			message.what = 0;
			handler.sendMessage(message);

		} else {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}

	@Override
	public void onRefresh() {
		page = 1;
		mHttpClient(page, sort);
	}

	@Override
	public void onLoadMore() {
		page++;
		if (page <= sumPage) {
			mHttpClient(page, sort);
		} else {
			news_xlistview.stopLoadMore();
			ToastUtils.TextToast(getActivity(), "已经到底了");
		}
	}

	// //加载数据
	private void mHttpClient(final int mpage, String msotrt) {
		String url = UCS.URLCOMMON + "msg/index/getinformationlist";
		String keys_[] = { "uid", "status", "page", "page_size" };
		String[] test_ = { uid, "", "1", "100" };
		if(msotrt!=null&&msotrt.equals("noread")){
			test_[1] = "1";
		}else{
			news_num.setVisibility(View.VISIBLE);
		}
		HttpUtils.upload(url, keys_, test_, new BackJson() {
			@Override
			public void backJson(String json) {
				try {
					if (json != null && json.startsWith("\ufeff")) {
						json = json.substring(1);
					}
					JSONObject mJson = new JSONObject(json);
					String msg = mJson.getString(UCS.MSG);
					int code = mJson.getInt(UCS.CODE);
					if (code == 1) {
						if (mpage == 1) {
							news.clear();
						}
						JSONArray data = mJson.getJSONArray(UCS.DATA);// ///
						Gson gson = new Gson();
						ArrayList<NewsInfo> mNews = new ArrayList<NewsInfo>();
						mNews = gson.fromJson(data.toString(),
								new TypeToken<ArrayList<NewsInfo>>() {
								}.getType());
						for (int i = 0; i < mNews.size(); i++) {
							Log.i(TAG, mNews.get(i).toString());
						}
						news.addAll(mNews);
						// newsAdapter.notifyDataSetChanged();
						if (mNews.size() == 0) {
						//	news_num.setVisibility(View.GONE);

							View layout = LayoutInflater.from(getActivity())
									.inflate(R.layout.nonedate, null);
							// if(mConserveFavorable.size()==0){
							total.removeAllViews();
							total.addView(layout);
							// }if(mConserveFavorable.size()!=0){
							// total.removeView(layout);
							// }
						} else {
							total.removeAllViews();
							total.addView(news_xlistview);
						}

						// joinInfos.addAll(mJoinInfos);
						setSumPage();
						mUpdateView();
					} else {
						ToastUtils.TextToast(getActivity(), msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					/*
					 * ToastUtils.TextToast(getActivity(), "解析异常");
					 */// 个别时候出现空指针，边缘测试，先忽略
				}
			}

		});
	}

	// 计算页数
	private void setSumPage() {
		if (num % offset == 0) {
			sumPage = num / offset;
		} else {
			sumPage = num / offset + 1;
		}
	}

	/*
	 * @Override public void onRefresh() { page=1; news.clear();
	 * mHttpClient(page, sort); }
	 * 
	 * @Override public void onLoadMore() { // TODO Auto-generated method stub
	 * page=page+1; mHttpClient(page, sort); }
	 *//*
	*//**
	 * Item点击事件
	 */
	/*
	 * @Override public void onRefresh() { page = 1; mHttpClient(page, sort); }
	 * 
	 * @Override public void onLoadMore() { page++; if (page <= sumPage) {
	 * mHttpClient(page, sort); } else { news_xlistview.stopLoadMore();
	 * ToastUtils.TextToast(getActivity(), "已经到底了"); } }
	 */
	/**
	 * Item点击事件
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		String id = news.get(position - 1).getId();
		String title = news.get(position - 1).getTitle();
		String content = news.get(position - 1).getContent();
		String insert_time = news.get(position - 1).getInsert_time();
		Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
		/*
		 * 修改信息数据的阅读状态 地址：http://app2.1jia2.cn/carpark/business/msg/index/
		 * informationmodifystatus 参数：  1. id  
		 */
		updateClient(id);
		intent.putExtra(UCS.ID, id);
		intent.putExtra("title", title);
		intent.putExtra("content", content);
		intent.putExtra("insert_time", insert_time);
		startActivity(intent);
	}

	private void updateClient(String msotrt) {
		String url = UCS.URLCOMMON + "msg/index/informationmodifystatus";
		String keys_[] = { "id" };
		String[] test_ = { msotrt };
		HttpUtils.upload(this.getActivity(), url, keys_, test_, new BackJson() {
			@Override
			public void backJson(String json) {
				try {
					if (json != null && json.startsWith("\ufeff")) {
						json = json.substring(1);
					}
					JSONObject mJson = new JSONObject(json);
					String msg = mJson.getString(UCS.MSG);
					int code = mJson.getInt(UCS.CODE);
					if (code == 1) {
						mHttpClient(1, "");
						numClient("");
					} else {
						ToastUtils.TextToast(getActivity(), msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtils.TextToast(getActivity(), "解析异常");
				}
			}

		});
	}

	// //适配器
	class NewsAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<NewsInfo> runPacks;

		public NewsAdapter(Context context, ArrayList<NewsInfo> runPacks) {
			this.context = context;
			this.runPacks = runPacks;
		}

		@Override
		public int getCount() {
			return runPacks.size();
		}

		@Override
		public Object getItem(int position) {
			return runPacks.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				convertView = View.inflate(context, R.layout.item_news, null);
			TextView name = (TextView) convertView
					.findViewById(R.id.news_content);
			name.setText(runPacks.get(position).getContent());
			TextView news_time = (TextView) convertView
					.findViewById(R.id.news_time);
			news_time.setText(runPacks.get(position).getInsert_time()
					.substring(0, 10));
			if (runPacks.get(position).getRead_status().equals("1")) {
				ImageView imageView = (ImageView) convertView
						.findViewById(R.id.news_flag);
				imageView.setBackgroundResource(R.drawable.message_box_01);
			}
			return convertView;
		}
	}

	/*
	 * 获取未读信息的数量
	 * 地址： http://app2.1jia2.cn/carpark/business/msg/index/getunreadinformationtotal
	 * 参数   1. uid      用户uid
	 */

	private void numClient(String msotrt) {
		String url = UCS.URLCOMMON + "msg/index/getunreadinformationtotal";
		String keys_[] = { "uid" };
		String[] test_ = { uid };
		HttpUtils.upload(this.getActivity(), url, keys_, test_, new BackJson() {
			@Override
			public void backJson(String json) {
				try {
					if (json != null && json.startsWith("\ufeff")) {
						json = json.substring(1);
					}
					JSONObject mJson = new JSONObject(json);
					String msg = mJson.getString(UCS.MSG);
					int code = mJson.getInt(UCS.CODE);
					if (code == 1) {
						String num = mJson.getString("data");
						news_num.setText(num + "条未读信息");
					} else {
						ToastUtils.TextToast(getActivity(), msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtils.TextToast(getActivity(), "解析异常");
				}
			}

		});
	}
}
