package zhongxin.m365.activity;

import org.json.JSONException;
import org.json.JSONObject;
import zhongxin.m365.R;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.InputControl;
import zhongxin.m365.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends BaseActivity {
/*
	@ViewInject(R.id.basegohome)
	private TextView basegohome;*/
	@ViewInject(R.id.suit)
	private Button suit;// 提交
	@ViewInject(R.id.content)
	private EditText content;
	private String uid;// 用户ID

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initview();
	}

	/**
	 * 设置标题 初始化UI
	 */
	public void initview() {
		basetitle.setText("意见反馈");
		//basegohome.setVisibility(TextView.GONE);
	}

	@OnClick({ R.id.suit })
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.suit:
			if (InputControl.isEmpty(content)) {
				ToastUtils.TextToast(getApplicationContext(), "请输入您的意见建议");
			} else {
				suitsuggestion();
			}
			break;

		}
	}

	/**
	 * 提交反馈的意见建议 意见反馈 'app_id'=>'1', 'content'=>'我在测试', 'add_uid'=>1
	 */
	private void suitsuggestion() {
		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		uid = preferences.getString("uid", null);
		String url = UCS.URLCOMMON + "opinion/index/add";
		String keys[] = { "uid", UCS.CONTENT };
		String contentstr = content.getText().toString();
		String values[] = { uid, contentstr };
		HttpUtils.upload(url, keys, values, new BackJson() {
			@Override
			public void backJson(String json) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(json);
					String codes = jsonObject.getString(UCS.CODE);
					if (codes.equals("1")) {
						ToastUtils.TextToast(getApplicationContext(), "提交成功");
						finish();
					} else {
						String msg = jsonObject.getString(UCS.MSG);
						ToastUtils.TextToast(getApplicationContext(), msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
