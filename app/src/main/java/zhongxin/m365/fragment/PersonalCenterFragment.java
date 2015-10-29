package zhongxin.m365.fragment;

import zhongxin.m365.R;
import zhongxin.m365.activity.MyAddressActivity;
import zhongxin.m365.activity.MyInfoActivity;
import zhongxin.m365.activity.SetActivity;
import zhongxin.m365.constant.UCS;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 个人中心
 *
 * @author zzq
 */
public class PersonalCenterFragment extends Fragment {// implements OnClickListener
    @ViewInject(R.id.basetitle)
    private TextView basetitle;
    /*	@ViewInject(R.id.storename)
        *//* 意见反馈 *//*
    private TextView storename;
	
	@ViewInject(R.id.center_back)
	*//* 意见反馈 *//*
	private TextView center_back;
	@ViewInject(R.id.center_renew)
	*//* 检查版本更新 *//*
	private TextView center_renew;
	@ViewInject(R.id.center_getout)
	*//* 检查版本更新 *//*
	private TextView center_getout;*/
    @ViewInject(R.id.baseback)
    private ImageView baseback;

    @ViewInject(R.id.righticon)
    private ImageView righticon;
    Context mContext;
    @ViewInject(R.id.my_info)
    private RelativeLayout my_info;
    @ViewInject(R.id.my_address)
    private LinearLayout my_address;
    String storeName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initUid();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalcenter, container, false); // 加载fragment布局
        ViewUtils.inject(this, view); // 注入view和事件
        initUid();
		/* 初始化子布局，判断已登录，或未登录，并通过注解添加点击事件 */
        // initchildview();
        //baseback.setVisibility(View.GONE);
        basetitle.setText("我的");
        righticon.setVisibility(View.VISIBLE);
        //storename.setText(storeName);

        mContext = this.getActivity();
        return view;
    }

    private void initUid() {// uid,mobile,address,name;
        mContext = this.getActivity();
        storeName = getActivity().getSharedPreferences(UCS.USERINFO,
                Activity.MODE_PRIVATE).getString("name", "");
    }

    @OnClick({R.id.my_info, R.id.my_address,R.id.righticon})//, R.id.center_back, R.id.center_renew
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.my_info:
                Intent myinfo = new Intent(getActivity(),
                        MyInfoActivity.class);
                startActivity(myinfo);
                break;
            case R.id.my_address:
                Intent myaddress = new Intent(getActivity(), MyAddressActivity.class);
                startActivity(myaddress);
                break;
            case R.id.righticon:
                Intent setting = new Intent(getActivity(), SetActivity.class);
                startActivity(setting);
                break;
            default:
                break;
        }
    }
	/*	case R.id.center_back:
		*//**//* 意见反馈 *//**//*
		*//**//*
		 * initUid(); if (uid == null || "".equals(uid)) { Intent intent = new
		 * Intent(getActivity(), LoginActivity.class); startActivity(intent); }
		 * else
		 *//**//*{
			Intent intentfeedsuggest = new Intent(getActivity(),
					FeedBackActivity.class);
			startActivity(intentfeedsuggest);
		}
			break;
		case R.id.center_renew:
			*//**//* 检查版本更新 *//**//*
			*//**//*
			 * AppUpdateUtils appUpdateUtils = new AppUpdateUtils(getActivity(),
			 * false); appUpdateUtils.getserviceapkcodeOrstartIntent();
			 *//**//*
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			    @Override
			    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
			        switch (updateStatus) {
			        case UpdateStatus.Yes: // has update
			            UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
			            break;
			        case UpdateStatus.No: // has no update
			            Toast.makeText(mContext, "当前已是最新版本。", Toast.LENGTH_SHORT).show();
			            break;
			        case UpdateStatus.NoneWifi: // none wifi
			            Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
			            break;
			        case UpdateStatus.Timeout: // time out
			            Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
			            break;
			        }
			    }

			});
			UmengUpdateAgent.update(this.getActivity());
			//ToastUtils.TextToast(getActivity(), "当前已是最新版本。");
			break;

		case R.id.center_getout:
			// 退出登录
			*//**//**
     * 清除已登录的所有信息
     *//**//*
			SharedPreferences user = getActivity().getSharedPreferences(
					UCS.USERINFO, Activity.MODE_PRIVATE);
			user.edit().clear().commit();
			Intent getout = new Intent(getActivity(), LoginActivity.class);
			getout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);

			startActivity(getout);
			// initchildview();
			break;

		}
	}*/

}
