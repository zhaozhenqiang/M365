package zhongxin.m365.activity;

import zhongxin.m365.R;
import zhongxin.m365.utils.MemoryManagementTools;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * BaseActivity 添加标题 监听标题点击事件 设置basetitle名字
 * 
 * @author zhaohuathinkpad
 * 
 */
public class BaseActivity extends FragmentActivity {
	private String tag = this.getClass().getSimpleName();
	public TextView basetitle; /* 标题 用于子类 设置标题 */
	public Button rightbtn;
	public ImageView righticon;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarLayout(R.layout.actionbar_layout);
		// 设置禁止横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置禁止横屏
		// 检测内存使用情况
		MemoryManagementTools.getMemory(tag, this);
		basetitle = (TextView) findViewById(R.id.basetitle);/* 标题 用于子类 设置标题 */
		rightbtn = (Button) findViewById(R.id.basegohome);
		righticon = (ImageView) findViewById(R.id.righticon);//

	}

	public void initview() {
		// TODO Auto-generated method stub

	}

	public void initdate() {
		// TODO Auto-generated method stub

	}

	public void getintentdate() {
		// TODO Auto-generated method stub

	}

	/**
	 * 配置文件中 添加有点击事件 android:onClick="onClick" 点击事件
	 * 
	 * */
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.baseback:
			// 返回上一页
			finish();
			break;
		}
	}

	/**
	 * 添加自定义标题 子类Activity中
	 * 如果调用"requestWindowFeature(Window.FEATURE_NO_TITLE);"则隐藏标题
	 */
	private void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getActionBar();// 高版本可以换成 ActionBar actionBar
												// =getActionBar();
		if (null != actionBar) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			LayoutInflater inflator = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			actionBar.setCustomView(v, layout);
			actionBar.setDisplayShowTitleEnabled(false);// 去除标题左侧图标
			// actionBar.setDisplayUseLogoEnabled(false);
			actionBar.show();
		}
	}

}
