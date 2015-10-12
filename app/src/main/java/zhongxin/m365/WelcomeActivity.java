package zhongxin.m365;

import zhongxin.m365.activity.LoginActivity;
import zhongxin.m365.constant.UCS;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 欢迎界面 检查更新 检查网络（设置SharedPreferences）
 * 
 * @author zzq 启动线程 跳转到 mainActivity
 */
@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends Activity {
	
	@ViewInject(R.id.big)
	private LinearLayout big;
	@ViewInject(R.id.start)
	private ImageView start;
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		SharedPreferences preferences = getSharedPreferences(
				"flag", Activity.MODE_PRIVATE);
		String flag = preferences.getString("flag", null);
		
		if(flag==null){
			Editor editor = preferences.edit();
			editor.putString("flag", "flag");
			editor.commit();
		}else{
			start.setVisibility(View.GONE);
			big.setBackgroundResource(R.drawable.lead_03);
		}
		
		startMainActivity();
	}

	protected void onPause() {
		super.onPause();
		finish();
	}

	private void startMainActivity() {
		// 启动MainActivity
		new Thread() {
			public void run() {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}// ///在此处做判断是否本地有存储SharedPreferences
				String uid = getSharedPreferences(UCS.USERINFO,
						Activity.MODE_PRIVATE).getString("uid", null);
				if (uid == null) {
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(intent);
					finish();
				}
			};
		}.start();
	}
}
