package zhongxin.m365.activity.older;

import java.util.ArrayList;
import org.json.JSONObject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.bean.older.Details;
import zhongxin.m365.bean.older.DetailsServe;
import zhongxin.m365.bean.older.PackageChildItem;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.ToastUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.views.ScrollViewWithListView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
@ContentView(R.layout.activity_servedetail)
public class ServeDetailActivity extends BaseActivity {
	static final String TAG = "ServeDetailActivity";

	private String id;
	Context mContext;
	// 用户名
	@ViewInject(R.id.username)
	private TextView username;
	// 手机号
	@ViewInject(R.id.order_phone)
	private TextView order_phone;
	// 车牌
	@ViewInject(R.id.car_number)
	private TextView car_number;
	// 车品牌
	@ViewInject(R.id.car_card)
	private TextView car_card;
	// 车系
	@ViewInject(R.id.car_class)
	private TextView car_class;
	// 付款方式
	@ViewInject(R.id.pay_class)
	private TextView pay_class;
	// 付款状态
	@ViewInject(R.id.pay_state)
	private TextView pay_state;
	// 订单状态
	@ViewInject(R.id.order_state)
	private TextView order_state;
	// 下单时间
	@ViewInject(R.id.order_time)
	private TextView order_time;
	// 付款时间
	@ViewInject(R.id.money_time)
	private TextView money_time;
	// 完成时间
	@ViewInject(R.id.done_time)
	private TextView done_time;
	// 完成时间
	@ViewInject(R.id.all_money)
	private TextView all_money;
	
	@ViewInject(R.id.packageinfo)
	private LinearLayout packageinfo;
	// 套餐信息
	@ViewInject(R.id.packagelistview)
	private ScrollViewWithListView packagelistview;
	// 服务信息
	@ViewInject(R.id.servelistview)
	private ScrollViewWithListView servelistview;
	private PackageAdapter packageAdapet;
	private ServeAdapter serveAdapet;
	/*
	private final static String tag="ServeWorkDetailsActivity";
	private ArrayList<ServeMessage> smArrayList=new ArrayList<ServeMessage>();
	private ArrayList<ServeM> sArrayList=new ArrayList<ServeM>();

*/
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		mContext = this;
		initData();
		initView();
		// mHttpClient();
	}

	private void initView() {
		basetitle.setText("服务工单详情");
	}

	private void initData() {
		id = getIntent().getStringExtra(UCS.ID);
		mHttpClient();
	}
/*
	@OnClick({ R.id.all, R.id.assigmenting, R.id.assigmented, R.id.righticon,
			R.id.startorder })
	private void mOnClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}
	*/
	
	private void mHttpClient() {
		String keys_[] = {"o_id"};
		String[] test_ = {id };
		String url = UCS.URLCOMMON + "order/ods/getorderinfo";
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
						JSONObject data = mJson.getJSONObject(UCS.DATA);// ///

						Gson gson = new Gson();
						Details detail = gson.fromJson(data.toString(),
								Details.class);
						username.setText(detail.getNickname());
						car_number.setText(detail.getPlate_num());
						if(detail.getCar_type()!=null&&!detail.getCar_type().equals("")){
							car_class.setText(detail.getCar_type());
						}
						if(detail.getCar_brand()!=null&&!detail.getCar_brand().equals("")){
							car_card.setText(detail.getCar_brand());
						}
						
						order_phone.setText(detail.getM_mobile());
						order_time.setText(detail.getCreate_time());
						pay_class.setText(detail.getPay_type_cn());
						pay_state.setText(detail.getPay_state_cn());
						order_state.setText(detail.getOd_state_cn());
						all_money.setText(all_money.getText().toString()+detail.getOd_money());
						
						
						
						if(detail.getCombo()==null||detail.getCombo().size()==0) {
							packageinfo.setVisibility(View.GONE);
						}
						else{
							packageAdapet = new PackageAdapter(mContext, detail.getCombo());
							packagelistview.setAdapter(packageAdapet);
					
						}
						serveAdapet = new ServeAdapter(mContext, detail.getServ());
						servelistview.setAdapter(serveAdapet);
						
						
						
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
	
	class ServeAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<DetailsServe> conserveFavorables;
	//	private String uid;

		public ServeAdapter(Context context,
				ArrayList<DetailsServe> conserveFavorables) {
			this.context = context;
			this.conserveFavorables = conserveFavorables;
/*			uid = this.context.getSharedPreferences(UCS.USERINFO,
					Activity.MODE_PRIVATE).getString(UCS.ID, "");*/
		}

		@Override
		public int getCount() {
			return conserveFavorables.size();
		}

		@Override
		public Object getItem(int position) {
			return conserveFavorables.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.item_statistic_twoclass, null);
			}
			TextView listview_name = (TextView) convertView
					.findViewById(R.id.listview_name);
			TextView listview_alreadyuse = (TextView) convertView
					.findViewById(R.id.listview_alreadyuse);
			TextView listview_take = (TextView) convertView
					.findViewById(R.id.listview_take);
			
			listview_name.setText(conserveFavorables.get(position).getServ_name());
			listview_alreadyuse.setText(conserveFavorables.get(position).getDiscount());
			//listview_take.setText(conserveFavorables.get(position).getPrice());
			
			double flag = Double.parseDouble(conserveFavorables.get(position).getDiscount());
			double num = Double.parseDouble(conserveFavorables.get(position).getNum());
			
			if(conserveFavorables.get(position).getIs_carsize().equals("2")){
				listview_take.setText(""+(Double.parseDouble(conserveFavorables.get(position).getPrice())*num-flag));
			}else{
				if(conserveFavorables.get(position).getCar_type().equals("2")){//da
					listview_take.setText(""+(Double.parseDouble(conserveFavorables.get(position).getB_price())*num-flag));
				}else{
					listview_take.setText(""+(Double.parseDouble(conserveFavorables.get(position).getS_price())*num-flag));
				}
			}
			
			
			return convertView;
		}
	}
	public class PackageAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<PackageChildItem> conserveFavorables;

		public PackageAdapter(Context context,
				ArrayList<PackageChildItem> conserveFavorables) {
			this.context = context;
			this.conserveFavorables = conserveFavorables;
		}

		@Override
		public int getCount() {
			return conserveFavorables.size();
		}

		@Override
		public Object getItem(int position) {
			return conserveFavorables.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.item_statistic_twoclass, null);
			}
			TextView listview_name = (TextView) convertView
					.findViewById(R.id.listview_name);
			TextView listview_alreadyuse = (TextView) convertView
					.findViewById(R.id.listview_alreadyuse);
			TextView listview_take = (TextView) convertView
					.findViewById(R.id.listview_take);
			
			listview_name.setText(conserveFavorables.get(position).getServ_name());
			listview_alreadyuse.setText(conserveFavorables.get(position).getUse_times());
			int t = 0;
			int al = 0;
			if(conserveFavorables.get(position)!=null&&conserveFavorables.get(position).getTotal_times()!=null&&!conserveFavorables.get(position).getTotal_times().equals("")){
				t = Integer.parseInt(conserveFavorables.get(position).getTotal_times());
			
			}
			if(conserveFavorables.get(position)!=null&&conserveFavorables.get(position).getUse_times()!=null&&!conserveFavorables.get(position).getUse_times().equals("")){
				al = Integer.parseInt(conserveFavorables.get(position).getUse_times());
			}

			listview_take.setText(""+(t-al));
			return convertView;
		}
	}
}
