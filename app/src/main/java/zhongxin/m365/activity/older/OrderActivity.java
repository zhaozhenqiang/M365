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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.activity.NewAddressActivity;
import zhongxin.m365.activity.RegisterActivity;
import zhongxin.m365.bean.older.Mo;
import zhongxin.m365.bean.older.OrderItem;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.ToastUtils;
import zhongxin.m365.views.XListView;
import zhongxin.m365.views.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseActivity implements IXListViewListener {

	static final String TAG = "OrderActivity";
	@ViewInject(R.id.all)
	private ImageView all;
	@ViewInject(R.id.assigmenting)
	private ImageView assigmenting;
	@ViewInject(R.id.assigmented)
	private ImageView assigmented;

	@ViewInject(R.id.startorder)
	private TextView startorder;

	@ViewInject(R.id.orderlistview)
	private XListView orderlistview;

	@ViewInject(R.id.righticon)
	private ImageView righticon;

	public ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();
/*	private String mid;
	private String mtitle;
	private String mcontent;
	private String minsert_time;*/
	private OrderAdapter adapter;
	Context mContext;

	@ViewInject(R.id.total)
	private LinearLayout total;
	
	int page = 1;// 加载更多与刷新之标志位； 1：刷新；>1：加载更多
	int handleFlag = 0;// 0 all; 1 handled; 2 no handled;
	// :"" all;1:no handled 9: already

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		mContext = this;
		initView();
		// mHttpClient(page, sort);// 请求数据
	//	mHttpClient();在resume里实现该方法。
	}

	private void initView() {
		basetitle.setText("预约单");
		righticon.setImageResource(R.drawable.reservation_l_01);
		righticon.setVisibility(View.VISIBLE);
		/*
		 * adapter = new OrderAdapter(OrderActivity.this, orderList);
		 * orderlistview.setAdapter(adapter);
		 */

		orderlistview.setPullLoadEnable(true);
		orderlistview.setXListViewListener(this);
		/* orderlistview.setOnItemClickListener(new mOnItemClickListener()); */
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
			assigmenting.setImageResource(R.drawable.reservation_l_03);
			handleFlag = 1;
			page = 1;
			mHttpClient();
			break;
		case R.id.assigmented:
			changebackground();
			assigmented.setImageResource(R.drawable.reservation_l_04);
			page = 1;
			handleFlag = 2;
			mHttpClient();
			break;
		case R.id.righticon:
		case R.id.startorder:
			Intent start = new Intent(this, RegisterActivity.class);
			startActivity(start);
		default:
			break;
		}
	}

	public void changebackground() {
		all.setImageResource(R.drawable.reservation_l_05);
		assigmenting.setImageResource(R.drawable.reservation_l_06);
		assigmented.setImageResource(R.drawable.reservation_l_07);

	}

	private void mHttpClient() {

		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		String pageValue = ""+page;
		String cust_id = preferences.getString("cust_id", null);
		String keys_[] = { "cust_id", "curPage", "od_line" };
		String[] test_ = { cust_id, pageValue, "" };
		String url = UCS.URLCOMMON + "order/index/getapttlist";
		if (handleFlag == 0) {
			test_[2] = "";
		}
		if (handleFlag == 1) {
			test_[2] = "9";
		}
		if (handleFlag == 2) {
			test_[2] = "1";
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
						ArrayList<OrderItem> mstoreInfos = new ArrayList<OrderItem>();
						mstoreInfos = gson.fromJson(data.toString(),
								new TypeToken<ArrayList<OrderItem>>() {
								}.getType());
						if(mstoreInfos.size()<1){
							orderlistview.stopLoadMore();
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
							total.addView(orderlistview);
						}
						
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
		//orderlistview.stopLoadMore();
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
				adapter = new OrderAdapter(OrderActivity.this, orderList);
				orderlistview.setAdapter(adapter);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
				String currentDate = df.format(new Date()); // 开始时间
				orderlistview.stopRefresh();
				orderlistview.setRefreshTime(currentDate);
				break;
			case 1:
				adapter.notifyDataSetChanged();
				orderlistview.stopLoadMore();
				break;
			default:
				break;
			}
		};
	};
	
	class OrderAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<OrderItem> order;
	//	private String uid;

		public OrderAdapter(Context context,
				ArrayList<OrderItem> order) {
			this.context = context;
			this.order = order;
	/*		uid = this.context.getSharedPreferences(UCS.USERINFO,
					Activity.MODE_PRIVATE).getString(UCS.ID, "");*/
		}

		@Override
		public int getCount() {
			//	return 5;
			return order.size();
		}

		@Override
		public Object getItem(int position) {
			return order.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
				convertView = View.inflate(context,
						R.layout.item_order, null);

			TextView name = (TextView) convertView
					.findViewById(R.id.name);
			TextView handle = (TextView) convertView
					.findViewById(R.id.tohandle);
			TextView time = (TextView) convertView
					.findViewById(R.id.time);
			TextView car = (TextView) convertView.findViewById(R.id.cardnumber);
			TextView content = (TextView) convertView
					.findViewById(R.id.content);
			
			name.setText(order.get(position).getNickname());
			time.setText(order.get(position).getNd_time());
			car.setText(order.get(position).getPlate_num());
			content.setText(order.get(position).getMo_str());
			//是否已经处理od_line 1:未处理；2：已处理；3：已取消---做了修改
			//！=1 隐藏
			if( !order.get(position).getOd_line().equals("1")){
				handle.setVisibility(View.GONE);
			}
			
			handle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//单属性：会员名；id;车牌号；时间；定金
					//串：
					for(int i=0;i<order.get(position).getMo().size();i++){
						
					}
					String text="",number="",id="",price="",s_price="",b_price="",is_size="",serveFavor="";
					//double all_money=0;
					for(Mo entry: order.get(position).getMo()){
						number+=("1"+",");
						text += (entry.getName()+" ");
						id+=(entry.getS_id()+",");
						price += (entry.getPrice()+",");
						b_price += (entry.getBvehicle_price()+",");
						s_price += (entry.getSvehicle_price()+",");
						serveFavor += ("0"+",");
						is_size+=(entry.getIs_carsize()+",");//
					}
					
					Intent intent = new Intent(context,NewAddressActivity.class);
						intent.putExtra("midValue", order.get(position).getM_id());
						intent.putExtra("mobileValue", order.get(position).getM_mobile());
						intent.putExtra("carnumValue", order.get(position).getPlate_num());
						intent.putExtra("timeValue", order.get(position).getNd_time());
						intent.putExtra("nameValue", order.get(position).getNickname());
						//新加入字段！！！
						intent.putExtra("orderIdValue", order.get(position).getId());
						intent.putExtra("moneyValue", order.get(position).getOd_money());
						
						intent.putExtra("textSelect", text);
						intent.putExtra("numberSelect", number);
						intent.putExtra("idSelect", id);
						intent.putExtra("favorSelect", serveFavor);
						intent.putExtra("price", price);
						intent.putExtra("b_price", b_price);
						intent.putExtra("s_price", s_price);
						intent.putExtra("is_size", is_size);
						context.startActivity(intent);
				}
			});
			return convertView;
		}

	}
	@Override
	protected void onResume() {
		super.onResume();
		mHttpClient();
	}

}
