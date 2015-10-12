package zhongxin.m365.basedialog;

import zhongxin.m365.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class DownLoadNoticeDialog extends DialogFragment {
	private Activity activity;
	FragmentTransaction ft;

	@SuppressLint("ValidFragment")
	public DownLoadNoticeDialog(Activity activity) {
		super();
		this.activity = activity;
		ft = activity.getFragmentManager().beginTransaction();
	}

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.setCancelable(true);
		// 主题样式
		int style = DialogFragment.STYLE_NO_TITLE, theme = android.R.style.Theme_Holo_Dialog;
		setStyle(style, theme);
	}

	/**
	 * 方法二 创建视图 设置文字
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icicle) {
		View v = inflater.inflate(R.layout.dialog_downloadnotice, container,
				false);
		return v;
	}

	// 显示对话框
	public void showDialog() {
		// TODO Auto-generated method stub
		super.show(ft, null);
	}

}
