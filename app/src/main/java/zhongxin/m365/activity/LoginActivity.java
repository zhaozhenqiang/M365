package zhongxin.m365.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import zhongxin.m365.MainActivity;
import zhongxin.m365.R;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.InputControl;
import zhongxin.m365.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

	public Context mContext;
	public static final String TAG = "LoginActivity";
	// 手机号,验证码，密码
	private String phoneSting,passwordString;
	@ViewInject(R.id.phone_number)
	private EditText phoneEt;
	@ViewInject(R.id.password_number)
	private EditText passwordEt;
	@ViewInject(R.id.sure)
	private Button sureBu;

	@ViewInject(R.id.look_password)
	private ImageView look_password;
	@ViewInject(R.id.look_frame)
	private FrameLayout look_frame;

	@ViewInject(R.id.forget)
	TextView forgetTv;
	@ViewInject(R.id.register)
	TextView registerTv;
	Intent intent;

	String phonePreString, passwordPreString;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		basetitle.setText("用户登录");
		initview();/* 初始化界面 设置标题（登录） */
		intent = new Intent(this, MainActivity.class);
		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		phonePreString = preferences.getString(UCS.MOBILE, "1");
		passwordPreString = preferences.getString(UCS.PASSWORD, "1");


	DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		float density= metric.density;// 屏幕密度（0.75 / 1.0 / 1.5）
		int densityDpi = metric.densityDpi;// 屏幕密度DPI（120 / 160 / 240）
		Toast.makeText(this,"屏幕密度： "+density+"； dpi： "+densityDpi,Toast.LENGTH_LONG).show();
		//Log("midu"+density+"dpi"+densityDpi);



	}

	/**
	 * 初始化界面 设置标题（登录）
	 */
	public void initview() {
	}

	/**
	 * 监听点击事件
	 */
	boolean passwordflag = false;
	String password = "";

	@OnClick({R.id.sure, R.id.forget,R.id.register,R.id.look_frame})
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.look_frame:
				if (!passwordflag) {

					look_password.setImageDrawable(getResources().getDrawable(R.mipmap.login_03_));
					passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					look_password.setImageDrawable(getResources().getDrawable(R.mipmap.login_03));

					passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				passwordflag = !passwordflag;
				passwordEt.postInvalidate();
				CharSequence text = passwordEt.getText();
				if (text instanceof Spannable) {
					Spannable spanText = (Spannable) text;
					Selection.setSelection(spanText, text.length());
				}
				break;

		/*
		 * case R.id.deletename_login: 删除用户名 loginname_login.setText(null);
		 * break; case R.id.deletepasd_login: 删除用户密码
		 * loginpasd_login.setText(null); break;
		 */
			case R.id.sure:
			/* 登录 */
				login();
/*				if (!InputControl.isPhoneNumber(loginname_login)) {
					ToastUtils.TextToast(getApplicationContext(), "请输入合法的手机号码");
				} else {
					if (!InputControl.isEmpty(loginname_login)
							&& (!InputControl.isEmpty(loginpasd_login))) {
						login();
					} else {
						ToastUtils.TextToast(getApplicationContext(), "请输入账户名，或密码");
					}
				}*/
				break;
			case R.id.forget:
			/* 找回密码，忘记密码 */
				Intent intentforgetPasd = new Intent(getApplicationContext(),
						ForgetPasdActivity.class);
				String ph = "";
				if (phoneEt.getText() != null && !phoneEt.getText().equals("")) {
					ph = phoneEt.getText().toString();
				}
				intentforgetPasd.putExtra(UCS.TEL, ph);// 将手机号码传过去
				intentforgetPasd.putExtra(UCS.TITLE, "找回密码");
				startActivity(intentforgetPasd);
				break;
			case R.id.register:
			/* 注册 */
				Intent intentregistertPasd = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(intentregistertPasd);
				break;
			default:
				break;
		}
	}

	private long exitTime = 0; // 系统推出时间

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				// Toast.makeText(context, text, duration)
				ToastUtils.defaultErrorImageToast(this, "再按一次返回键，退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				return true;
			}
		}
		return false;
	}

	/**
	 * 登录
	 */
	private void login() {

		/*String url = UCS.URLCOMMON + "login/index/login";
		String keys[] = {"username", "password"};
		// String pasd = MD5.getMD5(loginpasd_login.getText().toString());
		String values[] = {phoneEt.getText().toString(),
				phoneEt.getText().toString()};*/
		/*System.out.print(phoneEt+"text"+phoneEt.getText()+"string"+phonePreString+"..."+passwordPreString);
		if(!phoneEt.getText().toString().equals(phonePreString)||!passwordEt.getText().toString().equals(passwordPreString)){
			ToastUtils.ImageToast(this,R.mipmap.login_04,"账号或密码错误");
		}
		else{*/
			Intent main = new Intent(getApplicationContext(),MainActivity.class);
			main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 清空已存在的所有Activity

			startActivity(main);

		//}

/*		HttpUtils.upload(this, url, keys, values, new BackJson() {

			@Override
			public void backJson(String json) {

				JSONObject date = JsonParserUtils.parsetojsonobjerct(
						getApplicationContext(), json);
				if (date != null) {
					ToastUtils.TextToast(getApplicationContext(), "登录成功");
					*//* 将数据（用户名 用户Id 等用户信息）写入配置文件，并记录 时间 *//*
					try {
						String uid = date.getString("uid");
						String cust_id = date.getString("cust_id");
						String name = date.getString("name");// 用户头像图片地址
						String img_url = date.getString("img_url");
						String address = date.getString("address");
						String mobile = date.getString("mobile");
						String tel = date.getString("tel");
						SharedPreferences user = getSharedPreferences(
								UCS.USERINFO, Activity.MODE_PRIVATE);
						Editor editor = user.edit();
						editor.putString(UCS.MOBILE, loginname_login.getText()
								.toString());
						editor.putString("uid", uid);
						editor.putString("cust_id", cust_id);
						editor.putString("name", name);
						editor.putString("img_url", img_url);
						editor.putString("address", address);
						editor.putString("mobile", mobile);
						editor.putString("tel", tel);
						*//**
						 * { "code": 1, "msg": "登录成功！", "data": { "uid": "158",
						 * "cust_id": "1000046", "name": "小韩", "img_url": "",
						 * "address": "上海市浦东新区金沙湾路", "mobile": "15901641201",
						 * "tel": "65628888" } }
						 *//*
						// editor.putLong(UCS.TIME, time);// 存储本次登录的时间
						editor.commit();
						startActivity(intent);
						finish();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		});*/
	}
}
