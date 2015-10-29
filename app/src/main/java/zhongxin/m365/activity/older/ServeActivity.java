package zhongxin.m365.activity.older;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.activity.NewAddressActivity;
import zhongxin.m365.adapter.older.ServeAdapter;
import zhongxin.m365.bean.older.ServeItem;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.ToastUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.views.XListView;
import zhongxin.m365.views.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_serve)
public class ServeActivity extends BaseActivity implements IXListViewListener {

	static final String TAG = "ServeActivity";
	@ViewInject(R.id.all)
	private ImageView all;
	@ViewInject(R.id.assigmenting)
	private ImageView assigmenting;
	@ViewInject(R.id.assigmented)
	private ImageView assigmented;

	@ViewInject(R.id.startorder)
	private TextView startorder;

	@ViewInject(R.id.servelistview)
	private XListView servelistview;

	@ViewInject(R.id.righticon)
	private ImageView righticon;

	public ArrayList<ServeItem> orderList = new ArrayList<ServeItem>();
/*	private String mid;
	private String mtitle;
	private String mcontent;
	private String minsert_time;*/
	private ServeAdapter adapter;
	Context mContext;

	@ViewInject(R.id.total)
	private LinearLayout total;
	
	int page = 1;// 加载更多与刷新之标志位； 1：刷新；>1：加载更多
	int handleFlag = 0;// 0 all; 1 未结单; 2 已结单;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		mContext = this;
		initView();
		// mHttpClient(page, sort);// 请求数据
	//	mHttpClient();
	}

	private void initView() {
		basetitle.setText("服务工单");
		righticon.setImageResource(R.drawable.reservation_l_01);
		righticon.setVisibility(View.VISIBLE);
		/*
		 * adapter = new OrderAdapter(OrderActivity.this, orderList);
		 * servelistview.setAdapter(adapter);
		 */

		servelistview.setPullLoadEnable(true);
		servelistview.setXListViewListener(this);
		/////////////////////
		adapter = new ServeAdapter(ServeActivity.this, orderList);
		/* servelistview.setOnItemClickListener(new mOnItemClickListener()); */
		// 因为没有详情了，所以不需item点击监听，只需要在adapter里监听处理即可！
	}

	@OnClick({ R.id.all, R.id.assigmenting, R.id.assigmented, R.id.righticon,
			R.id.startorder })
	private void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.all:
			changebackground();
			all.setImageResource(R.drawable.reservation_l_02);
			handleFlag = 0;
			page = 1;
			mHttpClient();
			break;
		case R.id.assigmenting:
			changebackground();
			assigmenting.setImageResource(R.drawable.new_r_02);
			handleFlag = 2;
			page = 1;
			mHttpClient();
			break;
		case R.id.assigmented:
			changebackground();
			assigmented.setImageResource(R.drawable.new_r_03);
			handleFlag = 1;
			page = 1;
			mHttpClient();
			break;
		case R.id.righticon:

		case R.id.startorder:
			Intent start = new Intent(this, NewAddressActivity.class);
			startActivity(start);
		default:
			break;
		}
	}
	
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.baseback:
			Intent intent = new Intent(this, zhongxin.m365.MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		case R.id.basetitle:

			break;
		}
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
			Intent intent = new Intent(this, zhongxin.m365.MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			//overridePendingTransition(R.anim.nofade, R.anim.nofade);
			
			return true;
		}
		
		return super.onKeyUp(keyCode, event);
	}

	public void changebackground() {
		all.setImageResource(R.drawable.reservation_l_05);
		assigmenting.setImageResource(R.drawable.new_r_05);
		assigmented.setImageResource(R.drawable.new_r_06);

	}

	private void mHttpClient() {

		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		String pageValue = ""+page;
		String cust_id = preferences.getString("cust_id", null);
		String uid = preferences.getString("uid", null);
		String keys_[] = {"mid", "cust_id", "curPage", "page_size","seaOdState" };
		String[] test_ = {uid, cust_id, pageValue, "10" ,"" };
		String url = UCS.URLCOMMON + "order/ods/getodslist";
		////seaOdState	Int	Y	订单状态（1： 已下单、 2： 已完成、 3： 已取消）1:未结单2：已结单
		if (handleFlag == 0) {
			test_[4] = "";
		}
		if (handleFlag == 1) {
			test_[4] = "1";
		}
		if (handleFlag == 2) {
			test_[4] = "2";
		}
		HttpUtils.upload(this, url, keys_, test_, new BackJson() {

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
						JSONArray data = mJson.getJSONArray(UCS.DATA);// ///

						Gson gson = new Gson();
						ArrayList<ServeItem> mstoreInfos = new ArrayList<ServeItem>();
						mstoreInfos = gson.fromJson(data.toString(),
								new TypeToken<ArrayList<ServeItem>>() {
								}.getType());
						if(mstoreInfos.size()<1){
							servelistview.stopLoadMore();
						}
if(page==1){
	orderList.clear();
}
					
						orderList.addAll(mstoreInfos);
						
						if (orderList.size() == 0) {
						//	news_num.setVisibility(View.GONE);

							View layout = LayoutInflater.from(mContext)
									.inflate(R.layout.nonedate, null);
							// if(mConserveFavorable.size()==0){
							total.removeAllViews();
							total.addView(layout);
							// }if(mConserveFavorable.size()!=0){
							// total.removeView(layout);
							// }
						} else {
							total.removeAllViews();
							total.addView(servelistview);
						}
						adapter.notifyDataSetChanged();
						mUpdateView();

					} else {
						ToastUtils.TextToast(getApplicationContext(), msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtils.TextToast(getApplicationContext(), "解析异常");
				}
			}

		});
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		mHttpClient();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page += 1;
		mHttpClient();
		//servelistview.stopLoadMore();
	}

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

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				adapter = new ServeAdapter(ServeActivity.this, orderList);
				servelistview.setAdapter(adapter);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
				String currentDate = df.format(new Date()); // 开始时间
				servelistview.stopRefresh();
				servelistview.setRefreshTime(currentDate);
				break;
			case 1:
				adapter.notifyDataSetChanged();
				servelistview.stopLoadMore();
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onResume() {
		super.onResume();
		mHttpClient();
	}

}