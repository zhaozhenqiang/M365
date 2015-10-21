package zhongxin.m365.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import zhongxin.m365.R;

/**
 * Toast 提示信息 1、文本 2.文本+图片
 * 
 * @author zhaohuathinkpad
 * 
 */
public class ToastUtils {

	private static Toast toast = null;
	public static int LENGTH_LONG = Toast.LENGTH_LONG;
	private static int LENGTH_SHORT = Toast.LENGTH_SHORT;

	/**
	 * 普通文本消息提示
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void TextToast(Context context, String str) {
		// 创建一个Toast提示消息
		toast = Toast.makeText(context, str, 0);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 显示消息
		toast.setDuration(LENGTH_SHORT);
		toast.show();
	}

	/**
	 * 带图片消息提示
	 * 
	 * @param context
	 * @param ImageResourceId
	 * @param text
	 * @param duration
	 */
	public static void ImageToast(Context context, int ImageResourceId,
			String str) {
		// 创建一个Toast提示消息
/*		toast = Toast.makeText(context, str, 0);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 获取Toast提示消息里原有的View
		View toastView = toast.getView();
		// 创建一个ImageView
		ImageView img = new ImageView(context);
		img.setImageResource(ImageResourceId);
		// 创建一个LineLayout容器
		LinearLayout ll = new LinearLayout(context);
		// 向LinearLayout中添加ImageView和Toast原有的View
		ll.setGravity(Gravity.CENTER_VERTICAL);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(img);
		ll.addView(toastView);
		// 将LineLayout容器设置为toast的View
		toast.setView(ll);*/
		toast = Toast.makeText(context, str,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setPadding(10,20,10,20);
		imageCodeProject.setImageResource(ImageResourceId);
		toastView.addView(imageCodeProject, 0);
		toast.show();
		// 显示消息
		toast.show();
	}

	public static void defaultErrorImageToast(Context context, String str) {
		TextToast(context, str);

	}

}
