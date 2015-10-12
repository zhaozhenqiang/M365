package zhongxin.m365.selectimage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import zhongxin.m365.constant.UCS;

@ContentView(R.layout.activity_clip_image)
public class ClipImageActivity extends BaseActivity {
	public static final String TAG = "ClipImageActivity";

	/** 截图保存地址 */
	public static final String FILENAME = "/sdcard/carpackmerchant";
	/**
	 当前状态小作分析，前端图片拿不到，怎么开始基础功能；
	 如果做分享，拿不到网上的jar包做个屁；
	 除了这两点，咱还能做什么？？？
	 */
	@ViewInject(R.id.basetitle)
	private TextView basetitle;
	@ViewInject(R.id.mClipImageLayout)
	private ClipImageLayout mClipImageLayout;
	private String clip_image_uri;

	private String flag;
	private String uid, mid;

	// 优惠券id
	private int id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initData();
		initView();
	}


	int[] a = {9,3,4,11,12,332,32,1,2,5,21,321,123} ;
	public void test(int a){

	}

	private void initData() {
		clip_image_uri = getIntent().getStringExtra(UCS.CLIP_IMAGE_URI);
/*		mid = getSharedPreferences(UCS.USERINFO, Activity.MODE_PRIVATE)
				.getString(UCS.MID, null);
		uid = getSharedPreferences(UCS.USERINFO, Activity.MODE_PRIVATE)
				.getString(UCS.UID, null);
		clip_image_uri = getIntent().getStringExtra(UCS.CLIP_IMAGE_URI);
		flag = getIntent().getStringExtra(UCS.FLAG);
		id = getIntent().getIntExtra(UCS.ID, 0);*/
	}

	private void initView() {
		basetitle.setText("图片截取");
		mClipImageLayout.setmClipImageSrc(clip_image_uri);
	}

	@OnClick(R.id.btn_submit)
	private void mOnclick(View v) {

		if (v.getId() == R.id.btn_submit) {
			Bitmap bitmap = mClipImageLayout.clip();
			OutputStream stream = null;
			try {
				stream = new FileOutputStream(FILENAME);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			try {
				stream.close();
				Log.i(TAG, FILENAME);
			} catch (IOException e) {
				e.printStackTrace();
			}
/*			*//*if (UCS.PERSONALCENTERFRAGEMENT.equals(flag)) {
				Log.i(TAG, "个人中心start");
				mHttpClient();
				finish();
			}*//* else {

			}*/

		}
	}

	// 个人中心
	/*private void mHttpClient() {
		// TODO 等待接口调试
		RequestParams params = new RequestParams();
		HttpUtils http = new HttpUtils();
		params.addBodyParameter(UCS.ID, mid);
		params.addBodyParameter(UCS.TYPE, "1");

		params.addBodyParameter(UCS.AVATARS, new File(FILENAME));
		params.addBodyParameter("insert_id", uid);
		http.send(HttpRequest.HttpMethod.POST,
				"http://192.168.0.210:90/v2/core/img/img/index/uploadpic",
				params, new mRequestCallBack());
	}

	class mRequestCallBack extends RequestCallBack<String> {

		@Override
		public void onFailure(HttpException arg0, String arg1) {
			Log.i(TAG, "上传失败");
			Log.i(TAG, arg1);
			ToastUtils.TextToast(getApplicationContext(), arg1);
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			try {
				JSONObject mJson = new JSONObject(arg0.result);
				String msg = mJson.getString(UCS.MSG);
				int code = mJson.getInt(UCS.CODE);
				if (code == 1) {
					Log.i(TAG, msg);
					String img_url = mJson.getString(UCS.DATA);
					SharedPreferences preferences = getSharedPreferences(
							UCS.USERINFO, Activity.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putString(UCS.IMG_URL, img_url);
					editor.commit();
					ToastUtils.TextToast(getApplicationContext(), msg);
					// 发送广播 个人中心
					Intent intent = new Intent();
					intent.setAction(UCS.PERSONALCENTERFRAGEMENT);
					intent.putExtra(UCS.FILENAME, FILENAME);
					sendBroadcast(intent);
				} else {
					Log.i(TAG, msg);
					ToastUtils.TextToast(getApplicationContext(), msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ToastUtils.TextToast(getApplicationContext(), "解析异常");
			}
		}

	}*/
}
