package zhongxin.m365.basedialog;

import zhongxin.m365.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HelpDialogFragment extends DialogFragment implements
		View.OnClickListener {

	// /**
	// * 方法一
	// * 设置 文字 从资源文件中获取文字
	// * @param helpResId
	// * @return
	// */
	// public static HelpDialogFragment newInstance(int helpResId) {
	// HelpDialogFragment hdf = new HelpDialogFragment();
	// Bundle bundle = new Bundle();
	// bundle.putInt("help_resource", helpResId);
	// hdf.setArguments(bundle);
	//
	// return hdf;
	// }
	/**
	 * 方法二 设置 文字 自定义传入文字
	 * 
	 * @param helpResId
	 * @return
	 */
	public static HelpDialogFragment newInstance(String result) {
		HelpDialogFragment hdf = new HelpDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString("help_resource", result);
		hdf.setArguments(bundle);

		return hdf;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.setCancelable(true);
		// 主题样式
		// setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
		// int style = DialogFragment.STYLE_NORMAL, theme = 0;
		int style = DialogFragment.STYLE_NO_TITLE, theme = android.R.style.Theme_Holo_Dialog;

		setStyle(style, theme);
	}

	// /**
	// * 方法一
	// * 创建视图
	// * 设置文字
	// */
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle icicle) {
	// View v = inflater.inflate(R.layout.help_dialog, container, false);
	//
	// TextView tv = (TextView) v.findViewById(R.id.helpmessage);
	// tv.setText(getActivity().getResources().getText(
	// getArguments().getInt("help_resource")));
	// // tv.setText("你好");
	// Button closeBtn = (Button) v.findViewById(R.id.btn_close);
	// closeBtn.setOnClickListener(this);
	// return v;
	// }
	/**
	 * 方法二 创建视图 设置文字
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icicle) {
		View v = inflater.inflate(R.layout.dialog_help, container, false);

		TextView tv = (TextView) v.findViewById(R.id.helpmessage);
		tv.setText(getArguments().getString("help_resource"));
		getArguments().getString("help_resource");
		// tv.setText("你好");
		Button closeBtn = (Button) v.findViewById(R.id.btn_close);
		closeBtn.setOnClickListener(this);
		return v;
	}

	public void onClick(View v) {
		dismiss();
	}

	/**
	 * 加载对话框
	 */
	// public static void showUpLoadingdialog(Activity activity) {
	//
	// // TODO Auto-generated method stub
	// FragmentTransaction ft =
	// activity.getFragmentManager().beginTransaction();
	//
	// HelpDialogFragment hdf = HelpDialogFragment.newInstance(null);// 设置显示的文字
	//
	// hdf.show(ft, " HELP_DIALOG_TAG");
	// }
}
