package zhongxin.m365.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class MemoryManagementTools {

	public static void getMemory(String tag, Context context) {
		final ActivityManager activityManager = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(info);
		Log.i(tag, "系统剩余内存:" + (info.availMem >> 10) + "k");
		Log.i(tag, "系统是否处于低内存运行：" + info.lowMemory);
		Log.i(tag, "当系统剩余内存低于" + (info.threshold >> 10) + "时就看成低内存运行");
	}
}
