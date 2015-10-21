package zhongxin.m365.activity.older;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.bean.older.OrderCar;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.ToastUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_newservestepb)
public class NewServeStepBActivity extends BaseActivity {

	static final String TAG = "RegisterActivity";
	@ViewInject(R.id.basetitle)
	private TextView basetitle;
	Context mContext;
	@ViewInject(R.id.selectpackage)
	TextView selectpackage;
	@ViewInject(R.id.selectserve)
	TextView selectserve;
	@ViewInject(R.id.addorder)
	TextView addorder;
	boolean isUser = false;

	String timeValue = "";
	String nameValue = "";
	
	String packageNum = "",packageId="";//分别是来回传送套餐和服务；
	String serveNum = "",serveId = "",serveFavor="";

	String orderIdValue="",mobileValue = "", midValue = "", carnumValue = "",
			carsizeValue = "",moneyValue="0";

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
	}
	
	private void initData() {
		
		orderIdValue = getIntent().getStringExtra("orderIdValue");
		moneyValue = getIntent().getStringExtra("moneyValue");
		midValue = getIntent().getStringExtra("midValue");
		mobileValue = getIntent().getStringExtra("mobileValue");
		carnumValue = getIntent().getStringExtra("carnumValue");
		carsizeValue = getIntent().getStringExtra("carsizeValue");
		if(getIntent().getStringExtra("favorSelect")!=null){
			packageId="";
			packageNum="";
			
			serveFavor = getIntent().getStringExtra("favorSelect");
			serveId = getIntent().getStringExtra("idSelect");
			serveNum = getIntent().getStringExtra("numberSelect");
			price  = getIntent().getStringExtra("price");
			b_price  = getIntent().getStringExtra("b_price");
			s_price  = getIntent().getStringExtra("s_price");
			
			is_size = getIntent().getStringExtra("is_size");
			
			if(carsizeValue.contains("大车")){
				car_size = is_size.replace("1", "2");
				System.out.println("大"+car_size);
				
			}else{
				car_size = is_size.replace("2", "1");
				System.out.println("小"+car_size);
			}
			selectserve.setText("服 务（"
					+ getIntent().getStringExtra("textSelect") + "）");
			if(is_size!=null&&!is_size.equals("")){//2不区分；1区分
				
				String[] issize = is_size.split(",");
				String[] priceFlagStrings = new String[issize.length];
				String[] priceSame = new String[issize.length];
				//double priceFlag[]= new double[issize.length];
				if(carsizeValue.equals("大车")){
					priceFlagStrings =b_price.split(",") ;
					priceSame = price.split(",");
				}else{
					 priceFlagStrings =s_price.split(",") ;
					 priceSame = price.split(",");
				}
				double allMoney=0;
				//int[] s = new int[issize.length];
				for(int i=0;i<issize.length;i++){
					if(issize[i].equals("1")){
						allMoney += Double.parseDouble(priceFlagStrings[i]);
					}else{
						allMoney += Double.parseDouble(priceSame[i]);
					}
					System.out.println("*************************"+allMoney);
				}
				all_money=""+allMoney;
				System.out.println("***************"+all_money);

			}
		}
	}

	@OnClick({ R.id.selectserve, R.id.selectpackage, R.id.selectnumber, R.id.addorder })
	private void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.addorder:
			submitForm();
			break;
		case R.id.selectserve:
			Intent intent = new Intent(this, ServeSelectServeActivity.class);
			if (serveNum != null) {
				intent.putExtra("serveNum", serveNum);
				intent.putExtra("serveId", serveId);
				intent.putExtra("serveFavor", serveFavor);
				intent.putExtra("carsizeValue",carsizeValue);
			}
			startActivityForResult(intent, 4);
			break;
		case R.id.selectpackage:
			Intent intentPackage = new Intent(this, ServeSelectPackageActivity.class);
			if (packageNum != null) {
				intentPackage.putExtra("packageId", packageId);
				intentPackage.putExtra("packageNum", packageNum);
			}
			startActivityForResult(intentPackage, 5);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// requestCode标示请求的标示 resultCode表示有数据
		if (requestCode == 5 && resultCode == RESULT_OK) {

			packageNum = data.getExtras().getString("numberSelect");
			packageId = data.getExtras().getString("idSelect");
			selectpackage.setText("套 餐（"
					+ data.getExtras().getString("textSelect") + "）");
		}
		if (requestCode == 4 && resultCode == RESULT_OK) {
			serveFavor = data.getExtras().getString("favorSelect");
			serveId = data.getExtras().getString("idSelect");
			serveNum = data.getExtras().getString("numberSelect");
			
			all_money = data.getExtras().getString("all_money");
			price  = data.getExtras().getString("price");
			b_price  = data.getExtras().getString("b_price");
			s_price  = data.getExtras().getString("s_price");
			
			is_size = data.getExtras().getString("is_size");
			if(carsizeValue.equals("大车")){
				car_size = is_size.replace("1", "2");
			}else{
				car_size = is_size.replace("2", "1");
			}
			selectserve.setText("服 务（"
					+ data.getExtras().getString("textSelect") + "）");
		}
	}
String price = null,b_price = null,s_price=null,all_money=null,is_size=null,car_size;
	private void submitForm() {
		submitHttp();
	}
	/** 提交新的预约单 */
	private void submitHttp() {
		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		if(timeValue==null){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		timeValue = df.format(new Date());// 
		}
		String cust_id = preferences.getString("cust_id", null);
		String uid = preferences.getString("uid", null);
		String url = UCS.URLCOMMON + "order/ods/addods";//mobile可能应该是m_mobile
		String keys[] = {"aptt_id","m_id","m_mobile", "nd_time", "car_plate","aptt_money","car_id",////会员id，预约时间，车牌号//"aptt_money",
				
				"s_id","is_size","price",//is_size 所选服务是否区分大小车（1,2,）1：区分，2：不区分  怎么确定值
				"all_money","b_price","s_price",
				"buy_num","dis_count",
				//服务优惠串，数量串，id串，价格类型串？
				"car_size",//car_size串，是选了车型之后按照服务 个数 全部初始化为“1，1，1，或2，2，2，这样子吗？
				"ass_id","useServTime",//套餐id，次数
				"cust_id","create_id", "source" };
		String values[] = {orderIdValue,midValue,mobileValue,timeValue, carnumValue,moneyValue,"", //会员id，预约时间，车牌号//"aptt_money",
				
				serveId,is_size,price,
				all_money,b_price,s_price,
				serveNum,serveFavor,//服务优惠串，数量串，id串，价格类型串？car_size串，暂时不传
				car_size,
				packageId,packageNum,//套餐服务id即：ssid?，次数
				cust_id,uid, "4" };
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
						Intent serIntent = new Intent(mContext,ServeActivity.class);
						serIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						//serIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
						startActivity(serIntent);

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
