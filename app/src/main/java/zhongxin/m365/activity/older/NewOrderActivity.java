package zhongxin.m365.activity.older;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.adapter.older.CardNumberAdapter;
import zhongxin.m365.bean.older.OrderCar;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.JsonParserUtils;
import zhongxin.m365.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xia.ui.component.MyDateTimePickerDialog;
import com.xia.ui.component.MyDateTimePickerDialog.OnDateTimeSetListener;

@ContentView(R.layout.activity_neworder)
public class NewOrderActivity extends BaseActivity implements
		OnFocusChangeListener {

	static final String TAG = "NewOrderActivity";
	CardNumberAdapter adapter;
	@ViewInject(R.id.basetitle)
	private TextView basetitle;
	@ViewInject(R.id.arror)
	private ImageView arror;// 选择数量箭头
	@ViewInject(R.id.selectnumber)
	private LinearLayout selectnumber;// 选择车牌号
	@ViewInject(R.id.numbertype_view)
	TextView numbertype_view;
	@ViewInject(R.id.input_card)
	private EditText input_card;// 优惠数目
	private boolean isView = false;
	// 时间
	@ViewInject(R.id.selecttime)
	private LinearLayout selecttime;
	@ViewInject(R.id.time)
	private TextView time;
	Context mContext;

	@ViewInject(R.id.number_id)
	private EditText number_id;// 会员号，手机号
	@ViewInject(R.id.name)
	private EditText name;// 会员号，手机号

	@ViewInject(R.id.number_listview)
	ListView number_listview;

	@ViewInject(R.id.selectserve)
	TextView selectserve;

	@ViewInject(R.id.orderprice)
	EditText orderprice;
	@ViewInject(R.id.addorder)
	Button addorder;
	boolean isUser = false;

	String timeValue = null;
	String serveValue = null;
	String nameValue = null;

	String mobileValue = null, midValue = null, carnumValue = null,
			moneyValue = null;

	ArrayList<OrderCar> carListL = new ArrayList<OrderCar>();

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
		basetitle.setText("新建预约单");
		number_id.setOnFocusChangeListener(this);
		selecttime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new MyDateTimePickerDialog(mContext,
						new OnDateTimeSetListener() {

							@Override
							public void onDateTimeSet(int year,
									int monthOfYear, int dayOfMonth, int hour,
									int minute) {
								time.setText(year + "年" + monthOfYear + "月"
										+ dayOfMonth + "日" + hour + "时"
										+ minute + "分");
								String m = monthOfYear < 10 ? ("0" + monthOfYear)
										: "" + monthOfYear;
								String d = dayOfMonth < 10 ? ("0" + dayOfMonth)
										: "" + dayOfMonth;
								// String h = hour<10?("0"+hour):""+hour;
								// String min =
								// minute<10?("0"+minute):""+minute;
								timeValue = "" + year + "-" + m + "-" + d;// +"-"+h+"-"+min;
							}
						}).show();
			}
		});
		number_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				number_listview.setVisibility(View.GONE);
				numbertype_view.setText(carListL.get(position).getPlate_num());
				isView = false;
				for(int i=0;i<carListL.size();i++){
					carListL.get(i).setSelected(null);//i<<<position 能混了吗？？？？
				}
				carListL.get(position).setSelected("select");

				arror.setBackgroundResource(R.drawable.arrow_d);
				if (carListL.get(position).getPlate_num().equals("其他")) {
					input_card.setVisibility(View.VISIBLE);
					carnumValue = null;
				} else {
					input_card.setVisibility(View.GONE);
					input_card.setText("");
					carnumValue = carListL.get(position).getPlate_num();
				}
				adapter.notifyDataSetChanged();

			}
		});
	}

	private void initData() {
	}

	@OnClick({ R.id.selectserve, R.id.name, R.id.selectnumber, R.id.addorder })
	private void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.addorder:
			submitForm();
			
			break;
		case R.id.selectnumber:// 选择车牌
			if (isUser) {
				if (isView) {
					number_listview.setVisibility(View.GONE);
					isView = !isView;
					arror.setBackgroundResource(R.drawable.arrow_d);
				} else {
					isView = !isView;
					number_listview.setVisibility(View.VISIBLE);
					arror.setBackgroundResource(R.drawable.arrow_u);
				}
			} else {
				ToastUtils.TextToast(this, "请输入会员手机号");
			}
			break;
		case R.id.name:
			if (isUser) {
				ToastUtils.TextToast(this, "未获得会员信息，请检查您卡号或手机号的输入");
			} else {
				ToastUtils.TextToast(this, "请输入会员手机号");
			}
			break;
		case R.id.selectserve:
			Intent intent = new Intent(this, SelectServeActivity.class);
			if (serveValue != null) {
				intent.putExtra("serveValue", serveValue);
			}
			// 把返回数据存入Intent
			// intent.putExtra("mustid", cust_id);
			// intent.putExtra("mustname", cust_name);
			// startActivityForResult(intent, 1);
			startActivityForResult(intent, 0);
			/**
			 * case R.id.basegohome: //大于一秒方个通过 if (System.currentTimeMillis() -
			 * lastClick <= 1000) { ToastUtils.TextToast(this, "您已经提交"); return;
			 * } lastClick = System.currentTimeMillis(); submitForm();
			 */
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// requestCode标示请求的标示 resultCode表示有数据
		if (requestCode == 0 && resultCode == RESULT_OK) {
			serveValue = data.getExtras().getString("numberSelect");
			selectserve.setText("选择服务（"
					+ data.getExtras().getString("textSelect") + ")");
		}

	}

	private void submitForm() {
		if (number_id.getText().toString().equals("")) {
			ToastUtils.TextToast(this, "请输入用户手机号！");
			return ;
		}
		if (numbertype_view.getText().toString().equals("")) {
			ToastUtils.TextToast(this, "请输入车牌号！");
			return ;

		}
		if (time.getText().toString().equals("")) {
			ToastUtils.TextToast(this, "请选择时间！");
			return ;
}
		if (selectserve.getText().toString().equals("")) {
			ToastUtils.TextToast(this, "请选择服务！");
			return ;
		}
		if (carnumValue == null) {
			if (input_card.getText().toString().equals("")) {
				ToastUtils.TextToast(this, "请输入车牌号！");
				return ;
			} else {
				carnumValue = input_card.getText().toString();
			}
		}
		submitHttp();
		// if(type=null) ;
		/*
		 * if ("".equals(name) || name == null) { ToastUtils.TextToast(this,
		 * "请填写优惠券名称"); return; } if ("".equals(getcartime) || getcartime ==
		 * null) { ToastUtils.TextToast(this, "请选择优惠券有效开始日期"); return; } if
		 * ("".equals(backcartime) || backcartime == null) {
		 * ToastUtils.TextToast(this, "请选择优惠券有效结束日期"); return; } if (false ==
		 * typeflag) { ToastUtils.TextToast(this, "请选择优惠券数量"); return;
		 *///

	}//

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		switch (arg0.getId()) {
		case R.id.number_id:
			if (number_id.hasFocus() == false) {
				if (number_id == null
						|| number_id.getText().toString().equals("")) {
					ToastUtils.TextToast(this, "用户名不可为空");
				} else {
					infoHttp();
				}
			}
			break;
		default:
			break;
		}

	}

	/** 根据会员号或手机号，获得会员信息 */
	private void infoHttp() {
		String url = UCS.URLCOMMON + "order/index/getcarbymobile";
		String keys[] = { UCS.MOBILE };
		String values[] = { number_id.getText().toString() };
		isUser = false; // define is a user
		HttpUtils.upload(this, url, keys, values, new BackJson() {
			@Override
			public void backJson(String json) {
				try {
					JSONObject mJson = new JSONObject(json);
					String msg = mJson.getString(UCS.MSG);
					int code = mJson.getInt(UCS.CODE);
					if (code == 1) {
						Log.i(TAG, msg);
						JSONObject date = JsonParserUtils.parsetojsonobjerct(
								getApplicationContext(), json);
						if (date != null) {
							isUser = true;// define is a user
							JSONArray car = date.getJSONArray("car");// ///
							Gson gson = new Gson();
							String nickname = date.getString("nickname");
							nameValue = nickname;
							midValue = date.getString("id");
							mobileValue = date.getString("mobile");
						//	String card_no = date.getString("card_no");//该属性可获得，但是属于非必须上传属性，顾暂时不做处理
							ArrayList<OrderCar> carList = new ArrayList<OrderCar>();
							carList = gson.fromJson(car.toString(),
									new TypeToken<ArrayList<OrderCar>>() {
									}.getType());
							name.setText(nickname);
							OrderCar other = new OrderCar();
							other.setPlate_num("其他");
							carList.add(other);
							carListL = carList;
							adapter = new CardNumberAdapter(
									getApplicationContext(), carListL);
							number_listview.setAdapter(adapter);
							System.out.println("this is ok");
						}
					} else {
						Log.i(TAG, msg);
						ToastUtils
								.TextToast(getApplicationContext(), "该账号不存在!");
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtils.TextToast(getApplicationContext(), "解析异常");
				}
			}
		});
	}

	/** 提交新的预约单 */
	private void submitHttp() {
		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		String cust_id = preferences.getString("cust_id", null);
		String uid = preferences.getString("uid", null);
		if (orderprice.getText().toString().equals("")) {
			moneyValue = "0";
		} else {
			moneyValue = orderprice.getText().toString();
		}
		String url = UCS.URLCOMMON + "order/index/addaptt";
		String keys[] = { "cust_id", "car_id", "car_num", "m_id", UCS.MOBILE,
				"nd_time", "od_money", "servid", "insert_id", "source" };
		String values[] = { cust_id, "", carnumValue, midValue, mobileValue,
				timeValue, moneyValue, serveValue, uid, "4" };
		HttpUtils.upload(this, url, keys, values, new BackJson() {
			@Override
			public void backJson(String json) {
				try {
					JSONObject mJson = new JSONObject(json);
					String msg = mJson.getString(UCS.MSG);
					int code = mJson.getInt(UCS.CODE);
					if (code == 1) {
						Log.i(TAG, msg);
						ToastUtils.TextToast(getApplicationContext(), msg);
						System.out.println("this is ok");
						finish();

					} else {
						Log.i(TAG, msg);
						ToastUtils.TextToast(getApplicationContext(), msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtils.TextToast(getApplicationContext(), "解析异常");
				}
			}
		});
	}
}
