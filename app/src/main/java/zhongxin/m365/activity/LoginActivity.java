package zhongxin.m365.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import zhongxin.m365.MainActivity;
import zhongxin.m365.R;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.selectimage.PopUpActivity;
import zhongxin.m365.utils.InputControl;
import zhongxin.m365.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
	@ViewInject(R.id.loginname_login)
	private EditText loginname_login;/* 用户名 */
	@ViewInject(R.id.loginpasd_login)
	private EditText loginpasd_login;/* 用户密码 */
	@ViewInject(R.id.loginbtn_loginbtn)
	private Button loginbtn_loginbtn;/* 登录 */
	/*
	 * @ViewInject(R.id.regist_login) private TextView regist_login; 注册用户
	 */
	@ViewInject(R.id.forgetpasd_login)
	private TextView forgetpasd_login;/* 找回密码 */
	@ViewInject(R.id.basegohome)
	private TextView basegohome;
	Intent intent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		basetitle.setText("用户登录");
		initview();/* 初始化界面 设置标题（登录） */
		intent = new Intent(this, MainActivity.class);
	}

	/**
	 * 初始化界面 设置标题（登录）
	 */
	public void initview() {
		// System.out.println(UCS.URLCOMMON
		// + "内外网标志位显示********************************************");
		// basetitle.setText("登      录");
		// basegohome.setVisibility(TextView.GONE);
	}

	/**
	 * 监听点击事件
	 */
	boolean passwordflag = false;
	String password = "";

	@OnClick({R.id.loginbtn_loginbtn, R.id.forgetpasd_login,R.id.register, R.id.image, R.id.look_password})
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.look_password:
				if (!passwordflag) {
					loginpasd_login.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					loginpasd_login.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				passwordflag = !passwordflag;
				loginpasd_login.postInvalidate();
				CharSequence text = loginpasd_login.getText();
				if (text instanceof Spannable) {
					Spannable spanText = (Spannable) text;
					Selection.setSelection(spanText, text.length());
				}
				break;
			case R.id.image:
			/* 更换用户头像//当前采用本地存储的方式，ref,file,练练手O(∩_∩)O哈哈~！！！之后或考虑存在网络上 */
				Intent popuimage = new Intent(getApplicationContext(),
						PopUpActivity.class);
				popuimage.putExtra(UCS.FLAG, UCS.PERSONALCENTERFRAGEMENT);
				startActivity(popuimage);
				break;
		/*
		 * case R.id.deletename_login: 删除用户名 loginname_login.setText(null);
		 * break; case R.id.deletepasd_login: 删除用户密码
		 * loginpasd_login.setText(null); break;
		 */
			case R.id.loginbtn_loginbtn:
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
			case R.id.forgetpasd_login:
			/* 找回密码，忘记密码 */
				Intent intentforgetPasd = new Intent(getApplicationContext(),
						ForgetPasdActivity.class);
				String ph = "";
				if (loginname_login.getText() != null && !loginname_login.getText().equals("")) {
					ph = loginname_login.getText().toString();
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
		String url = UCS.URLCOMMON + "login/index/login";
		String keys[] = {"username", "password"};
		// String pasd = MD5.getMD5(loginpasd_login.getText().toString());
		String values[] = {loginname_login.getText().toString(),
				loginpasd_login.getText().toString()};
		if(!loginname_login.getText().toString().equals("123")){
			ToastUtils.ImageToast(this,R.mipmap.login_04,"账号或密码错误");
		}
		else{
			Intent main = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(main);

		}

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
