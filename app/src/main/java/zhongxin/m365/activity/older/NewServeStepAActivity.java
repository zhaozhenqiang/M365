package zhongxin.m365.activity.older;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
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
import zhongxin.m365.utils.JsonParserUtils;
import zhongxin.m365.utils.ToastUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_newservestepa)
public class NewServeStepAActivity extends BaseActivity implements
		OnFocusChangeListener {

	static final String TAG = "NewOrderActivity";
	CardNumberAdapter adapter;

	@ViewInject(R.id.orderprice)
	EditText orderprice;
	
	@ViewInject(R.id.notouchflag)
	private LinearLayout notouchflag;
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
	Context mContext;
	@ViewInject(R.id.card_number)
	private EditText card_number;// 会员号，手机号
	@ViewInject(R.id.name)
	private EditText name;// 会员号，手机号
	@ViewInject(R.id.number_id)
	private EditText number_id;// 会员号，手机号
	@ViewInject(R.id.number_listview)
	ListView number_listview;
	@ViewInject(R.id.addorder)
	Button addorder;
	boolean isUser = false;

	String serveValue = null;
	String nameValue = null;

	String mobileValue = null, midValue = null, carnumValue = null,carsizeValue=null;
String moneyValue="";
	
	@ViewInject(R.id.cararror)
	private ImageView cararror;// 选择数量箭头
	@ViewInject(R.id.selectcartype)
	private LinearLayout selectcartype;// 选择车牌号
	@ViewInject(R.id.finite_image)
	ImageView finite_image;
	@ViewInject(R.id.infinite_image)
	ImageView infinite_image;
	@ViewInject(R.id.cartype_view)
	TextView cartype_view;
	@ViewInject(R.id.finite_word)
	TextView finite_word;
	@ViewInject(R.id.infinite_word)
	TextView infinite_word;
	@ViewInject(R.id.favorable_numtype)
	private LinearLayout favorable_numtype;// 选择优惠券数量
	//private boolean typeflag = false;// 选类型则true;
	//private boolean numbertype = false;; // true 有限 2；false 无限 1
	private boolean isview = false;
	
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
		basetitle.setText("新建服务工单");
		card_number.setOnFocusChangeListener(this);
		number_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				number_listview.setVisibility(View.GONE);
				numbertype_view.setText(carListL.get(position).getPlate_num());
				isView = false;
				for (int i = 0; i < carListL.size(); i++) {
					carListL.get(i).setSelected(null);// i<<<position 能混了吗？？？？
				}
				carListL.get(position).setSelected("select");

				arror.setBackgroundResource(R.drawable.arrow_d);
				if (carListL.get(position).getPlate_num().equals("其他")) {
					input_card.setVisibility(View.VISIBLE);
					carnumValue = "其他";
				} else {
					input_card.setVisibility(View.GONE);
					input_card.setText("");
					carnumValue = carListL.get(position).getPlate_num();
				}
				adapter.notifyDataSetChanged();
			}
		});
	}
	String orderIdValue="", text="",number="",id="",price="",s_price="",b_price="",is_size="",serveFavor="";
	private void initData() {
		moneyValue = getIntent().getStringExtra("moneyValue");
		orderIdValue = getIntent().getStringExtra("orderIdValue");
		midValue = getIntent().getStringExtra("midValue");
		mobileValue = getIntent().getStringExtra("mobileValue");
		nameValue = getIntent().getStringExtra("nameValue");
		carnumValue = getIntent().getStringExtra("carnumValue");
		if(midValue!=null&&!midValue.equals("")){
			card_number.setText(mobileValue);
			name.setText(nameValue);
			numbertype_view.setText(carnumValue);
			orderprice.setText(moneyValue);
			card_number.setEnabled(false);
			name.setEnabled(false);
			numbertype_view.setEnabled(false);
			orderprice.setEnabled(false);
			selectnumber.setClickable(false);
			notouchflag.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtils.TextToast(mContext, "预约单仅有车型可以选择！");
					}
			});
		}
		
		text = getIntent().getStringExtra("textSelect");
		number = getIntent().getStringExtra("numberSelect");
		id = getIntent().getStringExtra("idSelect");
		serveFavor = getIntent().getStringExtra("favorSelect");
		price = getIntent().getStringExtra("price");
		b_price = getIntent().getStringExtra("b_price");
		s_price = getIntent().getStringExtra("s_price");
		is_size = getIntent().getStringExtra("is_size");
	}

	@OnClick({ R.id.name, R.id.selectnumber, R.id.addorder, R.id.selectcartype,R.id.add_favorable_infinite,R.id.add_favorable_finite })//addorder 下一步
	private void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.selectcartype://选择车牌
			if (isview) {
				isview = !isview;
				favorable_numtype.setVisibility(View.GONE);
				cararror.setBackgroundResource(R.drawable.arrow_d);
			} else {
				favorable_numtype.setVisibility(View.VISIBLE);
				isview = !isview;
				cararror.setBackgroundResource(R.drawable.arrow_u);
			}
			break;
		case R.id.add_favorable_finite://选其他，手动输入
			cararror.setBackgroundResource(R.drawable.arrow_d);
			finite_image.setVisibility(View.VISIBLE);
			infinite_image.setVisibility(View.GONE);
			cartype_view.setText(finite_word.getText().toString());
			//add_favorable_3.setVisibility(View.VISIBLE);
			favorable_numtype.setVisibility(View.GONE);
			finite_word.setTextColor(0xfffca00e);
			infinite_word.setTextColor(android.graphics.Color.GRAY);
			isview = false;
			carsizeValue="小车";
			break;
		case R.id.add_favorable_infinite://选默认
			//typeflag = true;
			//numbertype = false;
			isview = false;
			cararror.setBackgroundResource(R.drawable.arrow_d);
			infinite_image.setVisibility(View.VISIBLE);
			cartype_view.setText(infinite_word.getText().toString());
			finite_image.setVisibility(View.GONE);
			infinite_word.setTextColor(0xfffca00e);
			finite_word.setTextColor(android.graphics.Color.GRAY);
			//add_favorable_3.setVisibility(View.GONE);
			favorable_numtype.setVisibility(View.GONE);
			carsizeValue = "大车";
			break;
		case R.id.addorder:
		/*	Intent starts = new Intent(this, NewServeStepBActivity.class);
			startActivity(starts);
			break;*/
//			ToastUtils.TextToast(this, "待开发");
			//mobileValue = card_number.getText().toString();
			//midValue = null, carnumValue = null,carsizeValue=null;
			if(mobileValue==null){
				ToastUtils.TextToast(this, "请输入您的会员或手机号！");
				break;
			}
			if(carnumValue==null){
				ToastUtils.TextToast(this, "请选择您的车牌号！");
				break;
			}
			if(carnumValue.equals("其他")||carnumValue.equals("")){
				carnumValue = input_card.getText().toString();
			}
			if(carnumValue.equals("")){
				ToastUtils.TextToast(this, "请输入您的车牌号！");
				break;
			}
			if(carsizeValue==null){
				ToastUtils.TextToast(this, "请选择车型！");
				break;
			}
			Intent intent = new Intent(this, NewServeStepBActivity.class);
			
			intent.putExtra("orderIdValue", orderIdValue);
			
			if (orderprice.getText().toString()==null&&orderprice.getText().toString().equals("")) {
				moneyValue = "0";
			} else {
				moneyValue = orderprice.getText().toString();
			}
			intent.putExtra("moneyValue", moneyValue);
			
			intent.putExtra("carsizeValue", carsizeValue);
			intent.putExtra("mobileValue", mobileValue);
			intent.putExtra("midValue", midValue);
			intent.putExtra("carnumValue", carnumValue);
			intent.putExtra("textSelect", text);
			intent.putExtra("numberSelect", number);
			intent.putExtra("idSelect", id);
			intent.putExtra("favorSelect", serveFavor);
			intent.putExtra("price", price);
			intent.putExtra("b_price", b_price);
			intent.putExtra("s_price", s_price);
			intent.putExtra("is_size", is_size);
			startActivity(intent);

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
		}
	}


	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		switch (arg0.getId()) {
		case R.id.card_number:
			if (card_number.hasFocus() == false) {
				if (card_number == null
						|| card_number.getText().toString().equals("")) {
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
		String values[] = { card_number.getText().toString() };
		isUser = false; // define is a user
		/**
		 
		 {
    "code": 1,
    "msg": "查询成功！",
    "data": {
        "nickname": "忆小臣",
        "id": "1",
        "mobile": "18930614121",
        "card_no": "card112345655",
        "car": [
            {
                "car_id": "69",
                "plate_num": "沪A420av",
                "brand_id": "1",
                "type_id": "1",
                "carbrand": "奥迪",
                "cartype": "A6"
            },
            {
                "car_id": "74",
                "plate_num": "沪A19203",
                "brand_id": "1",
                "type_id": "1",
                "carbrand": "奥迪",
                "cartype": "A6"
            }
        ]
    }
}
		 
		 
		 
		 */
		
		
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
							//String card_no = date.getString("card_no"); //        "card_no": "card112345655",
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

}
