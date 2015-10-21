package zhongxin.m365.activity;

import org.json.JSONObject;

import zhongxin.m365.R;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.InputControl;
import zhongxin.m365.utils.JsonParserUtils;
import zhongxin.m365.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgetPasdActivity extends BaseActivity {
	private Button nextbtn;
	private EditText codeet, phoneet;
	private TextView getcodebtn;
	private String phonenumber, mcode;// 手机号
	public static final String TYPEONE = "1";// 注册
	public static final String TYPETWO = "2";// 修改
	public static final String TAG = "ForgetPasdActivity";
	boolean testFlag = false;
	@ViewInject(R.id.pasd)
	private EditText pasd;
	String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pasd);
		initData();
		initView();
		getintentdate();
	}

	private void initData() {
		basetitle.setText("忘记密码");
	}

	/**
	 * 获取传送过来的数据
	 */
	public void getintentdate() {
		if (getIntent().getExtras() != null) {
			phonenumber = getIntent().getExtras().getString(UCS.TEL);
			if (phonenumber != null) {
				phoneet.setText(phonenumber);
			}
		}
	}

	private void initView() {
		getcodebtn = (TextView) findViewById(R.id.getCode_forgrtpasd);
		nextbtn = (Button) findViewById(R.id.next_forgetpasd);
		phoneet = (EditText) findViewById(R.id.phone_forgetpasd);
		codeet = (EditText) findViewById(R.id.code_forgetpasd);
		pasd = (EditText) findViewById(R.id.pasd);
		Forgetpasdlistener l = new Forgetpasdlistener();
		nextbtn.setOnClickListener(l);
		getcodebtn.setOnClickListener(l);

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int i = msg.arg1;
			if (i >= 1) {
				getcodebtn.setClickable(false);
				getcodebtn.setText(i + "秒后重新发送");
			} else {
				getcodebtn.setClickable(true);
				getcodebtn.setText(R.string.getcode_regist);
			}
		};
	};

	class Forgetpasdlistener implements OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.getCode_forgrtpasd:
				// 获取验证码
				if (InputControl.isPhoneNumber(phoneet)) {
					isAccount();
				} else {
					ToastUtils.TextToast(getApplicationContext(), "请输入合法的手机号码");
				}

				break;
			case R.id.next_forgetpasd:
				if (InputControl.isPhoneNumber(phoneet)
						&& !InputControl.ISNumber(codeet)) {
					// 下一步
					String codeinput = codeet.getText().toString();// 用户输入的验证码
					if (mcode != null && mcode.equals(codeinput) || "".equals(codeinput)) {
						setPassword();
					} else {
						ToastUtils.TextToast(getApplicationContext(), "验证码有误!");
					}
				} else {
					ToastUtils.TextToast(getApplicationContext(),
							"请输入合法的手机号码,验证码!");
				}
				break;
			default:
				break;
			}
		}
	}

	private void setPassword() {
		String url = UCS.URLCOMMON + "user/index/modifypwd";
		String keys[] = { "uid", "pwd", "update_id", UCS.MOBILE, "vcode" };
		String pasdstr = pasd.getText().toString();
		// pasdstr = MD5.getMD5(pasdstr);
		// repasdstr = MD5.getMD5(repasdstr);
		String values[] = { uid, pasdstr, uid, phoneet.getText().toString(),
				codeet.getText().toString() };
		Log.i(TAG, "mobile[" + "]" + "pasdstr[" + pasdstr + "]" + "authcode["
				+ "]" + "repasdstr[" + "]");
		HttpUtils.upload(ForgetPasdActivity.this, url, keys, values,
				new BackJson() {

					@Override
					public void backJson(String json) {
						try {
							JSONObject mJson = new JSONObject(json);
							String msg = mJson.getString(UCS.MSG);
							int code = mJson.getInt(UCS.CODE);
							if (code == 1) {
								Log.i(TAG, msg);
								ToastUtils.TextToast(getApplicationContext(),
										msg);
								Intent intent = new Intent(
										getApplicationContext(),
										LoginActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 清空已存在的所有Activity
								startActivity(intent);
								// 清除原有信息
								SharedPreferences preferences = getSharedPreferences(
										UCS.USERINFO, Activity.MODE_PRIVATE);
								preferences.edit().clear().commit();
								finish();
							} else {
								Log.i(TAG, msg);
								ToastUtils.TextToast(getApplicationContext(),
										msg);
							}
						} catch (Exception e) {
							e.printStackTrace();
							ToastUtils.TextToast(getApplicationContext(),
									"解析异常");
						}
					}
				});
	}

	/** 判断是否已经注册 */
	private void isAccount() {
		String url_test = UCS.URLCOMMON + "user/index/ckmuser";
		String keys[] = { UCS.MOBILE };
		String values[] = { phoneet.getText().toString() };
		HttpUtils.upload(ForgetPasdActivity.this, url_test, keys, values,
				new BackJson() {

					@Override
					public void backJson(String json) {
						try {
							JSONObject mJson = new JSONObject(json);
							String msg = mJson.getString(UCS.MSG);
							int code = mJson.getInt(UCS.CODE);
							if (code == 1) {
								mcode = mJson.getString(UCS.DATA);
								Log.i(TAG, msg);
								testFlag = true;
								JSONObject date = JsonParserUtils
										.parsetojsonobjerct(
												getApplicationContext(), json);
								if (date != null) {
									ToastUtils.TextToast(
											getApplicationContext(),
											"手机号码已验证，正在请求验证码。");
									/* 将数据（用户名 用户Id 等用户信息）写入配置文件，并记录 时间 */
									uid = date.getString("id");
									// 判断有这个手机号用户，根据返回id确定uid,下一步是获得验证码
									getCode();
								}
								// getCodeControl();
							} else {
								Log.i(TAG, msg);
								ToastUtils.TextToast(getApplicationContext(),
										msg);
							}
						} catch (Exception e) {
							e.printStackTrace();
							ToastUtils.TextToast(getApplicationContext(),
									"解析异常");
						}
					}
				});

	}

	/** 获得验证码 */
	private void getCode() {

		String url = UCS.URLCOMMON + "vcode/index/vcode";
		String keys[] = { UCS.MOBILE };
		String values[] = { phoneet.getText().toString() };
		HttpUtils.upload(ForgetPasdActivity.this, url, keys, values,
				new BackJson() {

					@Override
					public void backJson(String json) {
						try {
							JSONObject mJson = new JSONObject(json);
							String msg = mJson.getString(UCS.MSG);
							int code = mJson.getInt(UCS.CODE);
							if (code == 1) {
								mcode = mJson.getString(UCS.DATA);
								Log.i(TAG, msg);
								testFlag = true;
								String date = mJson.getString("data");
								if (date != null) {// //////////
									getCodeControl();
									ToastUtils.TextToast(
											getApplicationContext(),
											"手机号码已验证，正在请求验证码。");
									/* 将数据（用户名 用户Id 等用户信息）写入配置文件，并记录 时间 */
									// 判断有这个手机号用户，根据返回id确定uid,下一步是获得验证码
								}
								// getCodeControl();
							} else {
								Log.i(TAG, msg);
								ToastUtils.TextToast(getApplicationContext(),
										msg);
							}
						} catch (Exception e) {
							e.printStackTrace();
							ToastUtils.TextToast(getApplicationContext(),
									"解析异常");
						}
					}
				});

	}

	/**
	 * 获取验证码按钮倒计时控制
	 */
	private void getCodeControl() {
		new Thread() {
			int i = 60;
			public void run() {
				try {
					while (i > 0) {
						Thread.sleep(1000);
						i--;
						Message message = new Message();
						message.arg1 = i;
						handler.sendMessage(message);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			};
		}.start();

	}
}
