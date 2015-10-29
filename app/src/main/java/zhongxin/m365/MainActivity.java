package zhongxin.m365;

import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.fragment.HomeFragment;
import zhongxin.m365.fragment.NewsFragment;
import zhongxin.m365.fragment.PersonalCenterFragment;
import zhongxin.m365.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
	@ViewInject(R.id.main_personal_message_iv)
	private TextView main_personal_message_iv;// 消息
	@ViewInject(R.id.main_home_iv)
	private TextView main_home_iv;// 首页
	@ViewInject(R.id.main_personal_center_iv)
	private TextView main_personal_center_iv;// 个人中心
	@ViewInject(R.id.main_order_iv)
	private TextView main_order_iv;//
	private FragmentManager fm;
	private HomeFragment homeFragment;
	private PersonalCenterFragment personalCenterFragment;
	private NewsFragment newsFragment;

	private long exitTime = 0; // 系统退出时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		fm = getSupportFragmentManager();
		initData();
		initFragment(homeFragment);

	}

	private void initData() {
		homeFragment = new HomeFragment();
		personalCenterFragment = new PersonalCenterFragment();
		newsFragment = new NewsFragment();
		//main_home_iv.setBackgroundResource(R.drawable.home_cur_2x);// 初始化
	}

	@OnClick({ R.id.main_home, R.id.main_personal_center,R.id.main_personal_message,R.id.main_order,R.id.main_center_iv })
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.main_personal_message:
			changebackground();
/*			main_personal_message_iv.setBackgroundResource(R.drawable.message_cur_2x);
			//changeFragment(newsFragment);*/
			break;
		/* 主页 */
		case R.id.main_home:
			changebackground();
/*			main_home_iv.setBackgroundResource(R.drawable.home_cur_2x);
			//changeFragment(homeFragment);*/
			break;
		/* 个人中心 */
		case R.id.main_personal_center:
			changebackground();

			Drawable topDrawable = getResources().getDrawable(R.mipmap.footer_05_);
			topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
			main_personal_center_iv.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
			main_personal_center_iv.setTextColor(getResources().getColor(R.color.maingreen));

			changeFragment(personalCenterFragment);
			break;
			case R.id.main_order:
				//changebackground();
				break;
			case R.id.main_center:
				changebackground();
				break;
		default:
			break;
		}
	}

	/**
	 * 点击控件背景
	 */
	public void changebackground() {
		Drawable topDrawable = getResources().getDrawable(R.mipmap.footer_05);
		topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
		main_personal_center_iv.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		main_home_iv.setTextColor(getResources().getColor(R.color.darkgray));
		main_personal_message_iv.setTextColor(getResources().getColor(R.color.darkgray));
		main_order_iv.setTextColor(getResources().getColor(R.color.darkgray));
		main_personal_center_iv.setTextColor(getResources().getColor(R.color.darkgray));


		topDrawable = getResources().getDrawable(R.mipmap.footer_01);
		topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
		main_home_iv.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null,null);

		topDrawable = getResources().getDrawable(R.mipmap.footer_02);
		topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
		main_order_iv.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null,null);

		topDrawable = getResources().getDrawable(R.mipmap.footer_04);
		topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
		main_personal_message_iv.setCompoundDrawablesWithIntrinsicBounds(null,topDrawable,null,null);

	}

	/* 初始化Fragment */
	private void initFragment(Fragment fragment) {
		changeFragment(fragment);
	}

	/* 设置Fragment */
	private void changeFragment(Fragment fragment) {
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.main_center, fragment);
		ft.commitAllowingStateLoss();
	}

	/**
	 * 再按一次退出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				// Toast.makeText(context, text, duration)
				ToastUtils.defaultErrorImageToast(getApplicationContext(),
						"再按一次返回键，退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
				return true;
			}
		}
		return false;
	}

	public void centerUse() {
		changebackground();
		main_personal_message_iv.setBackgroundResource(R.drawable.message_cur_2x);
		changeFragment(newsFragment);

	}
}