package zhongxin.m365.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import zhongxin.m365.MainActivity;
import zhongxin.m365.R;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.InputControl;
import zhongxin.m365.utils.ToastUtils;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

	public Context mContext;
	public static final String TAG = "RegisterActivity";
	// 手机号,验证码，密码
	private String phoneSting, codeString,passwordString;
	@ViewInject(R.id.phone_number)
	private EditText phoneEt;
	@ViewInject(R.id.code_number)
	private EditText codeEt;
	@ViewInject(R.id.password_number)
	private EditText passwordEt;
	@ViewInject(R.id.get_code)
	private TextView getcodeTv;
	@ViewInject(R.id.sure)
	private Button sureBu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		//setContentView(R.layout.activity_register);
		ToastUtils.TextToast(getApplicationContext(), "前期阶段，无验证码亦可以注册!");
		mContext = this;
		initData();
		initView();
	}

	private void initData() {
		basetitle.setText("用户注册");
	}


	private void initView() {
		Selflistener selflistener = new Selflistener();
		getcodeTv.setOnClickListener(selflistener);
		sureBu.setOnClickListener(selflistener);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int i = msg.arg1;
			if (i >= 1) {
				getcodeTv.setClickable(false);
				getcodeTv.setText(i + "秒后重新发送");
			} else {
				getcodeTv.setClickable(true);
				getcodeTv.setText(R.string.getcode_regist);
			}
		};
	};

	class Selflistener implements OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.get_code:
					// 获取验证码
					if (InputControl.isPhoneNumber(phoneEt)) {
						//isAccount();
						getCodeControl();
					} else {
						ToastUtils.TextToast(getApplicationContext(), "请输入合法的手机号码");
					}

					break;
				case R.id.sure:
					if (InputControl.isPhoneNumber(phoneEt) && !InputControl.ISNumber(phoneEt)) {
						// 下一步
						codeString = codeEt.getText().toString();// 用户输入的验证码
						phoneSting = phoneEt.getText().toString();
						passwordString = passwordEt.getText().toString();

						//if (codeString != null && !"".equals(codeString)) {
						if (passwordString != null && !"".equals(passwordString)) {
							SharedPreferences user = getSharedPreferences(
									UCS.USERINFO, Activity.MODE_PRIVATE);
							SharedPreferences.Editor editor = user.edit();
							editor.putString(UCS.MOBILE, phoneSting);// 存储用户名
							editor.putString(UCS.PASSWORD, passwordString);
							editor.commit();
							Intent main = new Intent(mContext, LoginActivity.class);
							main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 清空已存在的所有Activity
							startActivity(main);
							//ToastUtils.TextToast(getApplicationContext(), "前期阶段，无验证码亦可以注册!");
						} else {
							/*Intent main = new Intent(mContext, LoginActivity.class);
							main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 清空已存在的所有Activity
							startActivity(main);*/
							ToastUtils.TextToast(getApplicationContext(), "密码不可以为空!");
							//ToastUtils.TextToast(getApplicationContext(), "验证码有误!");
						}
					} else {
						ToastUtils.TextToast(getApplicationContext(),
								"请输入合法的手机号码!");
					}
					break;
				default:
					break;
			}
		}
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
/*
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

	}

	*//** 判断是否已经注册 *//*
	private void isAccount() {
		String url_test = UCS.URLCOMMON + "user/index/ckmuser";
		String keys[] = { UCS.MOBILE };
		String values[] = { phoneet.getText().toString() };

	}

	*//** 获得验证码 *//*
	private void getCode() {

		String url = UCS.URLCOMMON + "vcode/index/vcode";
		String keys[] = { UCS.MOBILE };
		String values[] = { phoneet.getText().toString() };

	}*/

}