package zhongxin.m365.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zhongxin.m365.constant.UCS;
import android.app.Activity;
import android.content.Context;

/**
 * 返回初步处理结果
 * 
 * @author Administrator
 * 
 */
public class JsonParserUtils {
	/**
	 * 返回String 类型的处理结果
	 * 
	 * @param activity
	 * @param json
	 * @return
	 */
	public static String parsetoString(Activity activity, String json) {

		// TODO Auto-generated method stub
		// {
		// "code": 3,
		// "msg": "开始时间格式错误！"
		// }
		JSONObject alldates;
		String result = null;
		try {
			alldates = new JSONObject(json);

			if (!alldates.isNull(UCS.CODE)) {
				if (alldates.getString(UCS.CODE).equals(UCS.CODE_VAL)) {
					// 实际所需要的结果
					result = alldates.getString(UCS.DATA);
				} else {
					String msg = alldates.getString(UCS.MSG);
					ToastUtils.TextToast(activity, msg);
				}
			} else {
				ToastUtils.TextToast(activity, "服务器异常");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 返回jsonobject
	 * 
	 * @param context
	 * @param json
	 * @return
	 */
	public static JSONObject parsetojsonobjerct(Context context, String json) {

		// TODO Auto-generated method stub
		// {
		// "code": 3,
		// "msg": "开始时间格式错误！"
		// }
		JSONObject alldates;
		JSONObject result = null;
		try {
			alldates = new JSONObject(json);

			if (!alldates.isNull(UCS.CODE)) {
				if (alldates.getString(UCS.CODE).equals(UCS.CODE_VAL)) {
					// 实际所需要的结果
					result = alldates.getJSONObject(UCS.DATA);
				} else {
					String msg = alldates.getString(UCS.MSG);
					ToastUtils.TextToast(context, msg);
				}
			} else {
				ToastUtils.TextToast(context, "服务器异常");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastUtils.TextToast(context, "服务器异常");

		}
		return result;

	}

	/**
	 * 返回jsonarray
	 * 
	 * @param activity
	 * @param json
	 * @return
	 */
	public static JSONArray parsetojsonarray(Activity activity, String json) {

		// TODO Auto-generated method stub
		// {
		// "code": 3,
		// "msg": "开始时间格式错误！"
		// }
		JSONObject alldates;
		JSONArray result = null;
		try {
			alldates = new JSONObject(json);

			if (!alldates.isNull(UCS.CODE)) {
				if (alldates.getString(UCS.CODE).equals(UCS.CODE_VAL)) {
					// 实际所需要的结果
					result = alldates.getJSONArray(UCS.DATA);
				} else {
					String msg = alldates.getString(UCS.MSG);
					ToastUtils.TextToast(activity, msg);
				}
			} else {
				ToastUtils.TextToast(activity, "服务器异常");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 返回是否成功
	 */
	public static boolean parsetoboolean(Activity activity, String json) {
		boolean flag = false;
		try {
			JSONObject object = new JSONObject(json);
			if (!object.isNull(UCS.CODE)
					&& object.getString(UCS.CODE).equals(UCS.CODE_VAL)) {
				flag = true;

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastUtils.TextToast(activity.getApplicationContext(), "服务器异常");
		}
		return flag;
	}

}
