package zhongxin.m365.basedialog;

import zhongxin.m365.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class BaseDialog extends Dialog {

	public BaseDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static void showDialogforSelect(final Activity activity,
			String title, String message, final DoselectOk doselectOk) {
		// TODO Auto-generated method stub
		AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(title)
				.setIcon(R.drawable.ic_launcher).setMessage(message)
				.setPositiveButton("返回", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消 取消操作
						dialog.cancel();
					}
				})
				.setNegativeButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 确认 在接口中完成 点击确定 索要实现的作用
						doselectOk.doselectok();

					}
				}).show();
		dialog.setCancelable(false);/* 设置禁止 触屏关闭，禁止按返回键关闭 */

	}

	public static void showDialogforCall(final Activity activity, String title,
			String message, final DoselectOk doselectOk) {
		// TODO Auto-generated method stub
		AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(title)
				.setIcon(R.drawable.ic_launcher).setMessage(message)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消 取消操作
						dialog.cancel();
					}
				})
				.setNegativeButton("拨打", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 确认 在接口中完成 点击确定 索要实现的作用
						doselectOk.doselectok();

					}
				}).show();

	}

	public static void showDialogforSelect3(final Activity activity,
			String title, String message, final DoselectOk doselectOk) {
		// TODO Auto-generated method stub
		AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(title)
				.setIcon(R.drawable.ic_launcher).setMessage(message)
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消 取消操作
						dialog.cancel();
					}
				})
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 确认 在接口中完成 点击确定 索要实现的作用
						doselectOk.doselectok();

					}
				}).show();

	}

	public static void showDialogToast(final Activity activity, String title,
			String message, final DoselectOk doselectOk) {
		AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(title)
				.setIcon(R.drawable.ic_launcher).setMessage(message)
				.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消 取消操作
						dialog.cancel();
					}
				}).show();
	}

	/**
	 * 
	 * @author zhaohuathinkpad 点击确定返回上一页
	 */
	public static void showDialogToBack(final Activity activity, String title,
			String message, final DoselectOk doselectOk) {
		AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(title)
				.setIcon(R.drawable.ic_launcher).setMessage(message)
				.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消 取消操作
						doselectOk.doselectok();
					}
				}).show();
		dialog.setCancelable(false);/* 设置禁止 触屏关闭，禁止按返回键关闭 */
	}

	public interface DoselectOk {
		public void doselectok();
	}
}
