package zhongxin.m365.fragment;

import zhongxin.m365.R;
import zhongxin.m365.activity.older.OrderActivity;
import zhongxin.m365.activity.older.ServeActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * 1jia2carpackmerchant 首页
 * 
 * @author zzq
 * 
 */
public class HomeFragment extends Fragment {

/*
	@ViewInject(R.id.basetitle)
	private TextView title;
	@ViewInject(R.id.baseback)
	private Button baseback;

	@ViewInject(R.id.order)
	private TextView order;
	@ViewInject(R.id.serve)
	private TextView serve;
*/

	private String uid, cust_id;
	String code;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// initUid();

	}

	/*
	 * private void initUid() { uid =
	 * getActivity().getSharedPreferences(UCS.USERINFO,
	 * Activity.MODE_PRIVATE).getString("uid", ""); cust_id =
	 * getActivity().getSharedPreferences(UCS.USERINFO,
	 * Activity.MODE_PRIVATE).getString("cust_id", ""); }
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(this, view);
		//baseback.setVisibility(View.GONE);
		//title.setText("汽车1+2ERP管理系统");
		return view;
	}

/*	@OnClick({ R.id.order, R.id.serve })
	private void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.order:
			Intent intent = new Intent(getActivity(), OrderActivity.class);
			startActivity(intent);
			break;
		case R.id.serve:
			Intent serve = new Intent(getActivity(), ServeActivity.class);
			startActivity(serve);
			//serve.setBackgroundColor(Color.BLACK);
			break;
		default:
			break;
		}
	}*/

}
